package io.bookwise.framework.config.adapter;

import io.bookwise.adapters.out.CreateUserAdapterOut;
import io.bookwise.application.usecase.CreateUserUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CreateUserAdapterConfig {

    @Bean
    public CreateUserUseCase createUserUseCase(CreateUserAdapterOut createUserAdapterOut) {
        return new CreateUserUseCase(createUserAdapterOut);
    }

}