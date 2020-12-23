package com.rolandopalermo.facturacion.ec.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static java.lang.String.format;

@EnableOAuth2Client
@Component
public class OAuth2Configuration {

    private final String grantType = "password";

    @Value("${veronica.base-url:#{null}}")
    private String accessTokenUri;

    @Value("${veronica.api.username:#{null}}")
    private String username;

    @Value("${veronica.api.password:#{null}}")
    private String password;

    @Value("${veronica.api.client-id:#{null}}")
    private String clientId;

    @Value("${veronica.api.client-secret:#{null}}")
    private String clientSecret;

    @Bean
    @Qualifier("OAuth2Configuration")
    protected ResourceOwnerPasswordResourceDetails resourceOwnerPasswordResourceDetails() {
        ResourceOwnerPasswordResourceDetails resource = new ResourceOwnerPasswordResourceDetails();
        resource.setAccessTokenUri(format(accessTokenUri, "api/v1.0/oauth/token"));
        resource.setClientId(clientId);
        resource.setClientSecret(clientSecret);
        resource.setGrantType(grantType);
        resource.setUsername(username);
        resource.setPassword(password);
        return resource;
    }

    @Bean
    @Qualifier("OAuth2ClientContext")
    protected OAuth2ClientContext oauth2ClientContext() {
        return new DefaultOAuth2ClientContext();
    }

    @Bean("OAuth2HttpClientRestTemplate")
    protected RestTemplate setUpHttpClientOAuthRestTemplate(
            @Qualifier("OAuth2Configuration") final ResourceOwnerPasswordResourceDetails resourceOwnerPasswordResourceDetails,
            @Qualifier("OAuth2ClientContext") final OAuth2ClientContext oauth2ClientContext) {
        return new OAuth2RestTemplate(resourceOwnerPasswordResourceDetails, oauth2ClientContext);
    }

}
