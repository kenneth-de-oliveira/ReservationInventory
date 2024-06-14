package io.bookwise.adapters.out.client;

public interface FeatureToggleClient {
    boolean isEnable(String featureToggleName);
}