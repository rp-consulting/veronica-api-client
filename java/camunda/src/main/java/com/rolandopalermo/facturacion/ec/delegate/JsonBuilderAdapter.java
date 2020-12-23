package com.rolandopalermo.facturacion.ec.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import static org.camunda.spin.Spin.JSON;

@Component
public class JsonBuilderAdapter implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String input = (String) execution.getVariable("input");
        String jsonRequest = JSON("{}").prop("contenido", JSON(input).prop("contenidoXmlCodificadoBase64").stringValue()).toString();
        execution.setVariable("jsonRequest", jsonRequest);
    }

}
