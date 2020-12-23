package com.rolandopalermo.facturacion.ec.service;

import com.rolandopalermo.facturacion.ec.dto.RequestDto;
import org.springframework.stereotype.Service;

@Service
public class SendReceiptServiceImpl extends CamundaServiceImpl<RequestDto, String> {

    @Override
    public String getResult(RequestDto requestDto) {
        return (String) run(requestDto);
    }

    @Override
    public String getProcessName() {
        return "proceso-emitir-comprobante";
    }

    @Override
    public boolean isValidInput(RequestDto requestDto) {
        return requestDto != null && requestDto.getContenidoXmlCodificadoBase64() != null;
    }

}
