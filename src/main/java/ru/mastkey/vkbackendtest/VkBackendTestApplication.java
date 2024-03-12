package ru.mastkey.vkbackendtest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class VkBackendTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(VkBackendTestApplication.class, args);
    }

}
