package com.rolandopalermo.facturacion.ec.config;

import com.rolandopalermo.facturacion.ec.service.ServicioAutenticacion;
import lombok.AllArgsConstructor;
import org.apache.camel.CamelContext;
import org.apache.camel.component.servlet.CamelHttpTransportServlet;
import org.apache.camel.impl.SimpleRegistry;
import org.apache.camel.spring.SpringCamelContext;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SuppressWarnings("rawtypes")
@Configuration
@AllArgsConstructor
public class CamelConfig {

    private final ApplicationContext context;
    private final ServicioAutenticacion servicioAutenticacion;

    @Bean
    public ServletRegistrationBean camelServletRegistrationBean() {
        ServletRegistrationBean registration = new ServletRegistrationBean(new CamelHttpTransportServlet(), "/api/*");
        registration.setName("CamelServlet");
        return registration;
    }

    @Bean
    public CamelContext camelContext() {
        SpringCamelContext springCamelContext = new SpringCamelContext(context);
        SimpleRegistry registry = new SimpleRegistry();
        registry.put("servicioAutenticacion", servicioAutenticacion);
        springCamelContext.setRegistry(registry);
        return springCamelContext;
    }

}
