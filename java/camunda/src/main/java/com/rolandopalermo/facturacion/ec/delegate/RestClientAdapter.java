package com.rolandopalermo.facturacion.ec.delegate;

import com.rolandopalermo.facturacion.ec.dto.ResponseDto;
import com.rolandopalermo.facturacion.ec.exception.UnsupportedHTTPMethodException;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class RestClientAdapter implements JavaDelegate {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    @Qualifier("OAuth2HttpClientRestTemplate")
    private RestTemplate httpClientRestTemplate;

    @Value("${veronica.base-url:#{null}}")
    private String baseUrl;

    public void execute(DelegateExecution execution) {
        String uri = String.format(baseUrl, execution.getVariable("httpUri").toString());
        String httpMethodValue = execution.getVariable("httpMethod").toString().toUpperCase();
        String payload = execution.getVariable("payload").toString();

        HttpMethod httpMethod = HttpMethod.valueOf(httpMethodValue);
        ResponseDto response;
        switch (httpMethod) {
            case GET:
                response = exchange(uri, HttpMethod.GET, null);
                break;
            case POST:
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                HttpEntity<String> entity = new HttpEntity<>(payload, headers);
                response = exchange(uri, HttpMethod.POST, entity);
                break;
            case PUT:
                response = exchange(uri, HttpMethod.PUT, null);
                break;
            case DELETE:
                response = exchange(uri, HttpMethod.DELETE, null);
                break;
            default:
                throw new UnsupportedHTTPMethodException(httpMethod);
        }
        execution.setVariable("httpStatusCode", response.getCodigoEstadoHttp());
        execution.setVariable("httpResponse", response.getRespuestaHttp());
    }

    private ResponseDto exchange(String uri, HttpMethod httpMethod, HttpEntity requestEntity) {
        ResponseDto response = new ResponseDto();
        try {
            ResponseEntity<String> exchange = httpClientRestTemplate.exchange(uri, httpMethod, requestEntity, String.class);
            response.setCodigoEstadoHttp(Integer.toString(exchange.getStatusCode().value()));
            response.setRespuestaHttp(exchange.getBody());
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            String httpStatusCode = Integer.toString(ex.getStatusCode().value());
            String httpResponse = ex.getResponseBodyAsString();
            LOGGER.error(String.format("Error requesting %s: %s\nHTTP Status Code: %s\nHTTP Response: %s",
                    httpMethod, uri, httpStatusCode, httpResponse));
            response.setCodigoEstadoHttp(httpStatusCode);
            response.setRespuestaHttp(httpResponse);
        }
        return response;
    }

}
