package io.bookwise.framework.config.adapter;

import io.bookwise.adapters.out.FindBookAdapterOut;
import io.bookwise.adapters.out.FindStudentAdapterOut;
import io.bookwise.adapters.out.ReservationMessageQueuePublisherAdapterOut;
import io.bookwise.application.core.ports.out.ReservationInventoryPortOut;
import io.bookwise.application.core.ports.out.SmtpMailMessagePortOut;
import io.bookwise.application.usecase.ReservationInventoryUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReservationInventoryAdapterConfig {
    @Bean
    public ReservationInventoryUseCase reservationInventoryUseCase(
            FindBookAdapterOut findBookAdapterOut,
            FindStudentAdapterOut findStudentAdapterOut,
            ReservationMessageQueuePublisherAdapterOut reservationMessageQueuePublisherAdapterOut,
            ReservationInventoryPortOut reservationInventoryPortOut,
            SmtpMailMessagePortOut smtpMailMessagePortOut
    ) {
        return new ReservationInventoryUseCase(
                findBookAdapterOut,
                findStudentAdapterOut,
                reservationMessageQueuePublisherAdapterOut,
                reservationInventoryPortOut,
                smtpMailMessagePortOut
        );
    }
}