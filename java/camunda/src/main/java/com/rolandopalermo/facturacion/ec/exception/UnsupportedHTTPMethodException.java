package com.rolandopalermo.facturacion.ec.exception;

import org.springframework.http.HttpMethod;

public class UnsupportedHTTPMethodException extends RuntimeException {

    public UnsupportedHTTPMethodException(HttpMethod httpMethod) {
        super(String.format("The method %s is not supported for this operation", httpMethod));
    }

}