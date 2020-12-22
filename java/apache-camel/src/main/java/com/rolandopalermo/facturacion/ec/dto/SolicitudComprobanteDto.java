package com.rolandopalermo.facturacion.ec.dto;

import lombok.Data;

@Data
public class SolicitudComprobanteDto {

    private String contenidoXmlCodificadoBase64;
    private boolean notificar;

}
