package io.bookwise.application.core.ports.out;

public interface FeatureTogglePortOut {
    boolean isEnabled(String featureToggleName);
}