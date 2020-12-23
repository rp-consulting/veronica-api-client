package com.rolandopalermo.facturacion.ec.service;

import java.io.Serializable;

public interface CamundaService<INPUT extends Serializable, OUTPUT extends Serializable> {

    Object run(INPUT input);

    OUTPUT getResult(INPUT input);

    String getProcessName();

    boolean isValidInput(INPUT input);

    String getInput();

    String getOutput();

}
