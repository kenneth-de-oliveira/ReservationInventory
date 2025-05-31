package io.bookwise.framework.config.adapter;

import io.bookwise.application.core.ports.out.CancelReservationPortOut;
import io.bookwise.application.usecase.CancelReservationUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CancelReservationAdapterConfig {
    @Bean
    public CancelReservationUseCase cancelReservationUseCase(CancelReservationPortOut cancelReservationPortOut) {
        return new CancelReservationUseCase(cancelReservationPortOut);
    }
}