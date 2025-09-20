package com.example.infrastructure.config.adapter;

import com.example.adapter.out.client.UserManagementServiceClient;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserManagementServiceConfig {

    @Value("${api.user-management-service.url}")
    private String url;

    @Bean
    public UserManagementServiceClient userManagementServiceClient() {
        return Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(UserManagementServiceClient.class, url);
    }

}