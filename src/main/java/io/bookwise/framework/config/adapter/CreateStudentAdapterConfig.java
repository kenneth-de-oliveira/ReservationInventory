package io.bookwise.framework.config.adapter;

import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import io.bookwise.adapters.out.CreateStudentAdapterOut;
import io.bookwise.adapters.out.FindAddressByPostalCodeAdapterOut;
import io.bookwise.adapters.out.client.FindAddressByPostalCodeClient;
import io.bookwise.application.core.ports.out.SmtpMailMessagePortOut;
import io.bookwise.application.usecase.CreateStudentUseCase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CreateStudentAdapterConfig {

    @Value("${api.viacep.url}")
    private String url;

    @Bean
    public CreateStudentUseCase createStudentUseCase(
                CreateStudentAdapterOut createStudentAdapterOut,
                FindAddressByPostalCodeAdapterOut findAddressByPostalCodeAdapterOut,
                SmtpMailMessagePortOut smtpMailMessagePortOut) {
        return new CreateStudentUseCase(
                createStudentAdapterOut,
                findAddressByPostalCodeAdapterOut,
                smtpMailMessagePortOut);
    }

    @Bean
    public FindAddressByPostalCodeClient findAddressByPostalCodeClient() {
        return Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(FindAddressByPostalCodeClient.class, url);
    }

}