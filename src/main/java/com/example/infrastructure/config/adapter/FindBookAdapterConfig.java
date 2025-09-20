package com.example.infrastructure.config.adapter;


import com.example.adapter.out.FeatureToggleAdapterOut;
import com.example.adapter.out.FindBookAdapterOut;
import com.example.application.core.port.out.ReservationInventoryPortOut;
import com.example.application.usecase.FindBookUseCase;
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