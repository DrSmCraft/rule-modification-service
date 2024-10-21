package com.cdss4pcp.rulemodificationservice.config;

import com.cdss4pcp.rulemodificationservice.parambuilder.ParamInjectorService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean("injectorService")
    public ParamInjectorService injectorService() {
        return new ParamInjectorService();
    }
}
