package io.bookwise.framework.config.adapter;

import io.bookwise.adapters.out.CreateStudentAdapterOut;
import io.bookwise.adapters.out.FindAddressByPostalCodeAdapterOut;
import io.bookwise.application.core.ports.out.SendMailMessagePortOut;
import io.bookwise.application.usecase.CreateStudentUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CreateStudentAdapterConfig {
    @Bean
    public CreateStudentUseCase createStudentUseCase(
                CreateStudentAdapterOut createStudentAdapterOut,
                FindAddressByPostalCodeAdapterOut findAddressByPostalCodeAdapterOut,
                SendMailMessagePortOut sendMailMessagePortOut) {
        return new CreateStudentUseCase(
                createStudentAdapterOut,
                findAddressByPostalCodeAdapterOut,
                sendMailMessagePortOut);
    }
}