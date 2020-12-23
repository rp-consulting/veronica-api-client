package com.rolandopalermo.facturacion.ec.controller;

import com.rolandopalermo.facturacion.ec.dto.RequestDto;
import com.rolandopalermo.facturacion.ec.service.CamundaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = {"api/comprobantes/"})
@RequiredArgsConstructor
public class ReceiptController {

    private final CamundaService camundaService;

    @PostMapping
    public ResponseEntity sendInvoice(@RequestBody RequestDto requestBody) {
        return new ResponseEntity(camundaService.getResult(requestBody), HttpStatus.OK);
    }

}
