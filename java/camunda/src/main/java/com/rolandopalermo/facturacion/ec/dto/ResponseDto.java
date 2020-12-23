package com.rolandopalermo.facturacion.ec.dto;

import lombok.Data;

@Data
public class ResponseDto extends BaseDto {

    private String codigoEstadoHttp;
    private String respuestaHttp;

}