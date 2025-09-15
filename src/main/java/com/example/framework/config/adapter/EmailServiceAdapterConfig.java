package com.example.framework.config.adapter;

import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import com.example.adapter.out.client.EmailServiceClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailServiceAdapterConfig {

    @Value("${api.email-service.url}")
    private String url;

    @Bean
    public EmailServiceClient emailServiceClient() {
        return Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(EmailServiceClient.class, url);
    }

}