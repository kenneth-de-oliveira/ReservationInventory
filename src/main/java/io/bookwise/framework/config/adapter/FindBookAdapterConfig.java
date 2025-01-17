package io.bookwise.framework.config.adapter;


import io.bookwise.adapters.out.FeatureToggleAdapterOut;
import io.bookwise.adapters.out.FindBookAdapterOut;
import io.bookwise.application.core.ports.out.ReservationInventoryPortOut;
import io.bookwise.application.usecase.FindBookUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FindBookAdapterConfig {
    @Bean
    public FindBookUseCase findBookUseCase(FindBookAdapterOut findBookAdapterOut,
                                           ReservationInventoryPortOut reservationInventoryPortOut,
                                           FeatureToggleAdapterOut featureToggleAdapterOut) {
        return new FindBookUseCase(findBookAdapterOut, reservationInventoryPortOut, featureToggleAdapterOut);
    }
}