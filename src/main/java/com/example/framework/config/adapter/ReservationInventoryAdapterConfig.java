package com.example.framework.config.adapter;

import com.example.adapter.out.FindBookAdapterOut;
import com.example.adapter.out.FindUserAdapterOut;
import com.example.adapter.out.ReservationMessageQueuePublisherAdapterOut;
import com.example.application.core.port.out.ReservationInventoryPortOut;
import com.example.application.core.port.out.EmailServicePortOut;
import com.example.application.usecase.ReservationInventoryUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReservationInventoryAdapterConfig {
    @Bean
    public ReservationInventoryUseCase reservationInventoryUseCase(
            FindBookAdapterOut findBookAdapterOut,
            FindUserAdapterOut findUserAdapterOut,
            ReservationMessageQueuePublisherAdapterOut reservationMessageQueuePublisherAdapterOut,
            ReservationInventoryPortOut reservationInventoryPortOut,
            EmailServicePortOut emailServicePortOut
    ) {
        return new ReservationInventoryUseCase(
                findBookAdapterOut,
                findUserAdapterOut,
                reservationMessageQueuePublisherAdapterOut,
                reservationInventoryPortOut,
                emailServicePortOut
        );
    }
}