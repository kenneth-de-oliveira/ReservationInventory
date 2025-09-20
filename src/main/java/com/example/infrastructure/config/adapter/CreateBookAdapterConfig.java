package com.example.infrastructure.config.adapter;

import com.example.adapter.out.CreateBookAdapterOut;
import com.example.application.usecase.CreateBookUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CreateBookAdapterConfig {
    @Bean
    public CreateBookUseCase createBookUseCase(CreateBookAdapterOut createBookAdapterOut) {
        return new CreateBookUseCase(createBookAdapterOut);
    }
}