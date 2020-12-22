package com.rolandopalermo.facturacion.ec.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.http.common.HttpOperationFailedException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
public class ProcesadorErroresHttp implements Processor {

    @Override
    public void process(Exchange exchange) {
        HttpOperationFailedException ex = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, HttpOperationFailedException.class);
        exchange.getIn().setHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        exchange.getIn().setBody(ex.getResponseBody());
    }

}
