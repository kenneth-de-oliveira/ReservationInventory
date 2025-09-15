package io.bookwise.framework.config.adapter;

import io.bookwise.adapters.out.FindBookAdapterOut;
import io.bookwise.adapters.out.FindUserAdapterOut;
import io.bookwise.adapters.out.ReservationMessageQueuePublisherAdapterOut;
import io.bookwise.application.core.ports.out.ReservationInventoryPortOut;
import io.bookwise.application.core.ports.out.EmailServicePortOut;
import io.bookwise.application.usecase.ReservationInventoryUseCase;
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