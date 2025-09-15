package com.example.framework.config.adapter;

import com.example.application.core.port.out.CancelReservationPortOut;
import com.example.application.core.port.out.FindBookPortOut;
import com.example.application.core.port.out.ReservationInventoryPortOut;
import com.example.application.usecase.CancelReservationUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CancelReservationAdapterConfig {
    @Bean
    public CancelReservationUseCase cancelReservationUseCase(
            CancelReservationPortOut cancelReservationPortOut,
            FindBookPortOut findBookPortOut,
            ReservationInventoryPortOut reservationInventoryPortOut
    ) {
        return new CancelReservationUseCase(
                cancelReservationPortOut,
                findBookPortOut,
                reservationInventoryPortOut
        );
    }
}