package com.rolandopalermo.facturacion.ec.common;

public interface Constantes {

    String RUTA_CREAR_COMPROBANTE = "direct:crearComprobante";
    String RUTA_ENVIAR_COMPROBANTE = "direct:enviarComprobante";
    String RUTA_AUTORIZAR_COMPROBANTE = "direct:autorizaciarComprobante";
    String RUTA_NOTIFICAR_COMPROBANTE = "direct:notificarComprobante";
    String RUTA_DUMMY = "http4://dummy.org";
    String CABECERA_AUTENTICACION = "Bearer ${bean:servicioAutenticacion?method=getAccessToken}";

}
