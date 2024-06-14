package io.bookwise.adapters.out.client.impl;

import io.bookwise.adapters.out.client.FeatureToggleClient;
import lombok.RequiredArgsConstructor;
import no.finn.unleash.DefaultUnleash;
import no.finn.unleash.util.UnleashConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FeatureToggleClientImpl implements FeatureToggleClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(FeatureToggleClientImpl.class);

    private final Environment env;

    @Override
    public boolean isEnable(String featureToggleName) {
        LOGGER.info("Checking if feature {} is enabled", featureToggleName);
        var isEnable = featureToggle().isEnabled(featureToggleName);
        LOGGER.info("Feature {} is enabled: {}", featureToggleName, isEnable);
        return isEnable;
    }

    private DefaultUnleash featureToggle() {
        return new DefaultUnleash(
                UnleashConfig.builder()
                        .appName(env.getProperty("featuretoggle.EnvName"))
                        .instanceId(env.getProperty("featuretoggle.InstanceId"))
                        .unleashAPI(env.getProperty("featuretoggle.Url"))
                        .build()
        );
    }

}