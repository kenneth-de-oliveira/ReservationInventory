package com.example.adapter.out;

import com.example.adapter.out.client.FeatureToggleClient;
import com.example.application.core.port.out.FeatureTogglePortOut;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class FeatureToggleAdapterOut implements FeatureTogglePortOut {

    private final FeatureToggleClient featureToggleClient;

    @Override
    public boolean isEnabled(String featureToggleName) {
        return featureToggleClient.isEnable(featureToggleName);
    }

}