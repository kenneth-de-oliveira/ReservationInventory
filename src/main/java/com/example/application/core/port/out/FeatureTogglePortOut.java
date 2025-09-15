package com.example.application.core.port.out;

public interface FeatureTogglePortOut {
    boolean isEnabled(String featureToggleName);
}