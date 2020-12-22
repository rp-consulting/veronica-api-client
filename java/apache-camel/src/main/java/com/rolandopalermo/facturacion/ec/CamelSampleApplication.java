package com.rolandopalermo.facturacion.ec;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class CamelSampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(CamelSampleApplication.class, args);
    }

}
