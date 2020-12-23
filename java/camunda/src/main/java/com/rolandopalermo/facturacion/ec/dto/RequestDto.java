package com.rolandopalermo.facturacion.ec.dto;

import lombok.Data;

@Data
public class RequestDto extends BaseDto {

    private byte[] contenidoXmlCodificadoBase64;
    private boolean notificar;

}
