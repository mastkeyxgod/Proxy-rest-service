package ru.mastkey.vkbackendtest.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "VkBackendTest",
                description = "Сaching proxy rest api", version = "1.0.0",
                contact = @Contact(
                        name = "Fetyukhin Ilya",
                        email = "ilya.fetyukhin@mail.ru",
                        url = "https://t.me/mastkeey"
                )
        )
)
public class OpenApiConfig {

}