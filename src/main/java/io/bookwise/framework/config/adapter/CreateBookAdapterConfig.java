package io.bookwise.framework.config.adapter;

import io.bookwise.adapters.out.CreateBookAdapterOut;
import io.bookwise.application.usecase.CreateBookUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CreateBookAdapterConfig {
    @Bean
    public CreateBookUseCase createBookUseCase(CreateBookAdapterOut createBookAdapterOut) {
        return new CreateBookUseCase(createBookAdapterOut);
    }
}