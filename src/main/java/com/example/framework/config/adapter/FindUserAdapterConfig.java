package com.example.framework.config.adapter;


import com.example.adapter.out.FindUserAdapterOut;
import com.example.application.usecase.FindUserUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FindUserAdapterConfig {

    @Bean
    public FindUserUseCase findUserUseCase(FindUserAdapterOut findUserAdapterOut) {
        return new FindUserUseCase(findUserAdapterOut);
    }

}