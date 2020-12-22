package com.rolandopalermo.facturacion.ec.route;

import com.rolandopalermo.facturacion.ec.dto.ComprobanteDto;
import com.rolandopalermo.facturacion.ec.processor.ProcesadorErroresHttp;
import com.rolandopalermo.facturacion.ec.processor.ProcesadorSolicitud;
import com.rolandopalermo.facturacion.ec.service.ServicioAutenticacion;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.http.common.HttpOperationFailedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import static com.rolandopalermo.facturacion.ec.common.Constantes.CABECERA_AUTENTICACION;
import static com.rolandopalermo.facturacion.ec.common.Constantes.RUTA_AUTORIZAR_COMPROBANTE;
import static com.rolandopalermo.facturacion.ec.common.Constantes.RUTA_CREAR_COMPROBANTE;
import static com.rolandopalermo.facturacion.ec.common.Constantes.RUTA_DUMMY;
import static com.rolandopalermo.facturacion.ec.common.Constantes.RUTA_ENVIAR_COMPROBANTE;
import static com.rolandopalermo.facturacion.ec.common.Constantes.RUTA_NOTIFICAR_COMPROBANTE;
import static java.lang.String.format;

@Slf4j
@Component
@RequiredArgsConstructor
public class MainRoute extends RouteBuilder {

    @Value("${veronica.base-url}")
    private String veronicaBaseUrl;
    private final ProcesadorSolicitud procesadorSolicitud;
    private final ProcesadorErroresHttp procesadorErroresHttp;
    private final ServicioAutenticacion servicioAutenticacion;

    @Override
    public void configure() {
        restConfiguration()
                .component("servlet")
                .apiContextPath("/api-doc")
                .apiProperty("api.title", "Integración de Apache Camel con Verónica API")
                .apiProperty("api.version", "1.0.0")
                .apiProperty("cors", "true");

        rest("comprobantes")
                .post()
                .description("Ejecuta el proceso completo de emisión de un comprobante electrónico")
                .consumes(MediaType.TEXT_PLAIN_VALUE)
                .produces(MediaType.APPLICATION_JSON_VALUE)
                .route()
                .process(procesadorSolicitud).marshal(new JacksonDataFormat(ComprobanteDto.class))
                .to(RUTA_CREAR_COMPROBANTE)
                .endRest();

        from(RUTA_CREAR_COMPROBANTE)
                .removeHeader("CamelHttpPath")
                .setHeader("Authorization", simple(CABECERA_AUTENTICACION))
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                .setHeader(Exchange.HTTP_URI, simple(format("%s/api/v1.0/comprobantes", veronicaBaseUrl)))
                .doTry()
                    .to(RUTA_DUMMY)
                        .choice()
                            .when().simple("${header.CamelHttpResponseCode} == 201")
                                .setHeader("accessKey").jsonpath("$.result.claveAcceso", false, String.class)
                                .to(RUTA_ENVIAR_COMPROBANTE)
                .endDoTry()
                .doCatch(HttpOperationFailedException.class)
                    .choice()
                        .when(this::isTokenExpired)
                            //Refrescar el token y reintentar
                            .bean(servicioAutenticacion, "refreshToken").to(RUTA_CREAR_COMPROBANTE)
                    .otherwise()
                        .process(procesadorErroresHttp)
                .end();

        from(RUTA_ENVIAR_COMPROBANTE)
                .removeHeader("CamelHttpPath")
                .setHeader("Authorization", simple(CABECERA_AUTENTICACION))
                .setHeader(Exchange.HTTP_METHOD, constant("PUT"))
                .setHeader(Exchange.HTTP_URI, simple(format("%s/api/v1.0/comprobantes/${header.accessKey}/enviar", veronicaBaseUrl)))
                .doTry()
                    .to(RUTA_DUMMY)
                    .convertBodyTo(String.class)
                    .choice()
                        .when().jsonpath("$.result[?(@.estado == 'RECIBIDA')]", true)
                            .to(RUTA_AUTORIZAR_COMPROBANTE)
                .endDoTry()
                .doCatch(HttpOperationFailedException.class)
                    .choice()
                        .when(this::isTokenExpired)
                            .bean(servicioAutenticacion, "refreshToken").to(RUTA_ENVIAR_COMPROBANTE)
                        .otherwise()
                            .process(procesadorErroresHttp)
                .end();

        from(RUTA_AUTORIZAR_COMPROBANTE)
                .removeHeader("CamelHttpPath")
                .setHeader("Authorization", simple(CABECERA_AUTENTICACION))
                .setHeader(Exchange.HTTP_METHOD, constant("PUT"))
                .setHeader(Exchange.HTTP_URI, simple(format("%s/api/v1.0/comprobantes/${header.accessKey}/autorizar", veronicaBaseUrl)))
                .doTry()
                    .to(RUTA_DUMMY)
                    .choice()
                        .when().simple("${header.CamelHttpResponseCode} == 200 and ${header.notificar}")
                            .convertBodyTo(String.class)
                            .setProperty("authResponseBody", body())
                            .to(RUTA_NOTIFICAR_COMPROBANTE)
                .endDoTry()
                .doCatch(HttpOperationFailedException.class)
                    .choice()
                        .when(this::isTokenExpired)
                            .bean(servicioAutenticacion, "refreshToken").to(RUTA_AUTORIZAR_COMPROBANTE)
                        .otherwise()
                            .process(procesadorErroresHttp)
                .end();

        from(RUTA_NOTIFICAR_COMPROBANTE)
                .removeHeader("CamelHttpPath")
                .setHeader("Authorization", simple(CABECERA_AUTENTICACION))
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                .setHeader(Exchange.HTTP_URI, simple(format("%s/api/v1.0/comprobantes/${header.accessKey}/notificar", veronicaBaseUrl)))
                .doTry()
                    .to(RUTA_DUMMY)
                    .process(exchange -> exchange.getIn().setBody(exchange.getProperty("authResponseBody")))
                .endDoTry()
                .doCatch(HttpOperationFailedException.class)
                    .choice()
                        .when(this::isTokenExpired)
                            .bean(servicioAutenticacion, "refreshToken").to(RUTA_NOTIFICAR_COMPROBANTE)
                        .otherwise()
                            .process(procesadorErroresHttp)
                .end();
    }

    private boolean isTokenExpired(Exchange exchange) {
        HttpOperationFailedException ex = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, HttpOperationFailedException.class);
        return (ex.getStatusCode() == HttpStatus.UNAUTHORIZED.value());
    }

}
