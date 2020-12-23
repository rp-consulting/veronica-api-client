package com.rolandopalermo.facturacion.ec.service;

import com.rolandopalermo.facturacion.ec.exception.InvalidInputException;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstanceWithVariables;
import org.camunda.bpm.engine.variable.VariableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.io.Serializable;

import static org.camunda.spin.Spin.JSON;

public abstract class CamundaServiceImpl<INPUT extends Serializable, OUTPUT extends Serializable> implements CamundaService<INPUT, OUTPUT> {

    @Autowired
    private ProcessEngine camunda;

    private static final String ERROR = "error";

    @Override
    public Object run(INPUT input) {
        if (!isValidInput(input)) {
            throw new InvalidInputException();
        }
        String json = JSON(input).toString();
        RuntimeService runtimeService = camunda.getRuntimeService();
        ProcessInstanceWithVariables processInstance = runtimeService.createProcessInstanceByKey(getProcessName())
                .setVariable(getInput(), json)
                .executeWithVariablesInReturn();
        VariableMap map = processInstance.getVariables();
        return !StringUtils.isEmpty(map.get(getOutput())) ? map.get(getOutput()) : map.get(ERROR);
    }

    @Override
    public String getInput() {
        return "input";
    }

    @Override
    public String getOutput() {
        return "output";
    }

}