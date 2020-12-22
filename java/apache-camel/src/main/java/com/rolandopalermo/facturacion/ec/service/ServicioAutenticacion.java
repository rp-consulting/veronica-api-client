package com.rolandopalermo.facturacion.ec.service;

import com.rolandopalermo.facturacion.ec.dto.TokenAccesoDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.Base64;

import static java.lang.String.format;

@Slf4j
@Service
@RequiredArgsConstructor
public class ServicioAutenticacion {

    private TokenAccesoDto token;

    private HttpEntity httpEntity;

    @Value("${veronica.base-url}")
    private String veronicaBaseUrl;

    @Value("${veronica.api.client-id}")
    private String veronicaApiClientId;

    @Value("${veronica.api.client-secret}")
    private String veronicaApiClientSecret;

    @Value("${veronica.api.username}")
    private String veronicaApiUsername;

    @Value("${veronica.api.password}")
    private String veronicaApiPassword;

    private static String basicAuthHeader;

    private final RestTemplate restTemplate;

    private final String LOG_ERROR_MESSAGE = "Falló la invocación a la API, status: {}, respuesta:\n{}";

    @PostConstruct
    public void init() {
        basicAuthHeader = Base64.getEncoder().encodeToString((veronicaApiClientId + ":" + veronicaApiClientSecret).getBytes());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + basicAuthHeader);
        httpEntity = new HttpEntity(null, headers);
        authenticate();
    }

    public void refreshToken() {
        try {
            String url = "%s/api/v1.0/oauth/token?refresh_token=%s&grant_type=refresh_token";
            ResponseEntity<TokenAccesoDto> responseBody = restTemplate.exchange(format(url, veronicaBaseUrl, token.getRefresh_token()), HttpMethod.POST, httpEntity, TokenAccesoDto.class);
            setToken(responseBody.getBody());
        } catch (HttpClientErrorException ex) {
            authenticate();
        }
    }

    public void authenticate() {
        try {
            String url = "%s/api/v1.0/oauth/token?username=%s&password=%s&grant_type=password";
            ResponseEntity<TokenAccesoDto> responseBody = restTemplate.exchange(
                    format(url, veronicaBaseUrl, veronicaApiUsername, veronicaApiPassword),
                    HttpMethod.POST,
                    httpEntity,
                    TokenAccesoDto.class);
            setToken(responseBody.getBody());
        } catch (HttpClientErrorException ex) {
            log.error(LOG_ERROR_MESSAGE, ex.getStatusCode(), ex.getResponseBodyAsString());
        }
    }

    public TokenAccesoDto getToken() {
        return token;
    }

    public void setToken(TokenAccesoDto token) {
        this.token = token;
    }

    public String getAccessToken() {
        return token.getAccess_token();
    }

}
