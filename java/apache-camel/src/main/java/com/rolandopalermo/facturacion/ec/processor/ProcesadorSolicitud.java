package com.rolandopalermo.facturacion.ec.processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rolandopalermo.facturacion.ec.dto.ComprobanteDto;
import com.rolandopalermo.facturacion.ec.dto.SolicitudComprobanteDto;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component
public class ProcesadorSolicitud implements Processor {

    /**
     * Cada vez que enviamos una solicitud para emitir un comprobante electrónico, este procesador se encargará
     * de extraer las variables que le permitirán ejecutar la carga, envío, autorización y posterior notificación
     * del comprobante electrónico
     * @param exchange
     * @throws Exception
     */
    @Override
    public void process(Exchange exchange) throws Exception {
        String requestBody = exchange.getIn().getBody(String.class).replaceAll("[\\r\\n]+", "");
        ObjectMapper objectMapper = new ObjectMapper();
        SolicitudComprobanteDto solicitudComprobanteDto = objectMapper.readValue(requestBody, SolicitudComprobanteDto.class);

        ComprobanteDto comprobanteDTO = new ComprobanteDto();
        comprobanteDTO.setContenido(solicitudComprobanteDto.getContenidoXmlCodificadoBase64());

        exchange.getIn().setHeader("notificar", solicitudComprobanteDto.isNotificar());
        exchange.getIn().setBody(comprobanteDTO);
    }

}
