package ru.mastkey.vkbackendtest.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
public class ApplicationConfiguration {
    @Bean
    CloseableHttpClient httpClient() {
        return HttpClients.createDefault();
    }

    @Bean
    ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

}
