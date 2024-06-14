package io.bookwise.adapters.out;

import io.bookwise.adapters.out.client.FeatureToggleClient;
import io.bookwise.application.core.ports.out.FeatureTogglePortOut;
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