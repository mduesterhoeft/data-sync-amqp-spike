package com.epages;

import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

@Component
public class DefaultRepositoryRestConfigurer extends RepositoryRestConfigurerAdapter {

    @Override
    public void configureJacksonObjectMapper(ObjectMapper objectMapper) {

        objectMapper.registerModule(parameterNamesModule());
    }

    @Bean
    public Module parameterNamesModule() {
        return new ParameterNamesModule();
    }
}