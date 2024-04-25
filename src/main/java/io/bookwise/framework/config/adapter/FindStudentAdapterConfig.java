package io.bookwise.framework.config.adapter;


import io.bookwise.adapters.out.FindStudentAdapterOut;
import io.bookwise.application.usecase.FindStudentUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FindStudentAdapterConfig {
    @Bean
    public FindStudentUseCase findStudentByDocumentUseCase(FindStudentAdapterOut findStudentByDocumentAdapterOut) {
        return new FindStudentUseCase(findStudentByDocumentAdapterOut);
    }
}