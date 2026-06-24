package com.ecommerce.project.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean // we made it a bean so we can autowire it anywhere. We will autowirte in CategoryServiceImpl
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
