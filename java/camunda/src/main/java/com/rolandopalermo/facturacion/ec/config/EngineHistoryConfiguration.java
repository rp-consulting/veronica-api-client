package com.rolandopalermo.facturacion.ec.config;

import org.camunda.bpm.engine.ProcessEngineConfiguration;
import org.camunda.bpm.engine.spring.SpringProcessEngineConfiguration;
import org.camunda.bpm.spring.boot.starter.configuration.CamundaHistoryLevelAutoHandlingConfiguration;
import org.camunda.bpm.spring.boot.starter.configuration.impl.AbstractCamundaConfiguration;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EngineHistoryConfiguration extends AbstractCamundaConfiguration implements CamundaHistoryLevelAutoHandlingConfiguration {

    @Override
    public void preInit(SpringProcessEngineConfiguration configuration) {
        configuration.setHistory(ProcessEngineConfiguration.HISTORY_NONE);
    }

}
