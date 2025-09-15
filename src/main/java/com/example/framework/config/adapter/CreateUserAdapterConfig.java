package com.example.framework.config.adapter;

import com.example.adapter.out.CreateUserAdapterOut;
import com.example.application.usecase.CreateUserUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CreateUserAdapterConfig {

    @Bean
    public CreateUserUseCase createUserUseCase(CreateUserAdapterOut createUserAdapterOut) {
        return new CreateUserUseCase(createUserAdapterOut);
    }

}