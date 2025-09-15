package io.bookwise.framework.config.adapter;


import io.bookwise.adapters.out.FindUserAdapterOut;
import io.bookwise.application.usecase.FindUserUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FindUserAdapterConfig {

    @Bean
    public FindUserUseCase findUserUseCase(FindUserAdapterOut findUserAdapterOut) {
        return new FindUserUseCase(findUserAdapterOut);
    }

}