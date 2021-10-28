package com.goganesh.packages;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
        "com.goganesh.packages.controller",
        "com.goganesh.packages.service",
        "com.goganesh.packages.configuration"
})
public class PlantUmlGeneratorApp {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(PlantUmlGeneratorApp.class, args);
    }
}
