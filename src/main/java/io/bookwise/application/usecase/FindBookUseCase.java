package io.bookwise.application.usecase;

import io.bookwise.application.core.domain.Book;
import io.bookwise.application.core.ports.in.FindBookPortIn;
import io.bookwise.application.core.ports.out.FeatureTogglePortOut;
import io.bookwise.application.core.ports.out.FindBookPortOut;

public class FindBookUseCase implements FindBookPortIn {

    private final FindBookPortOut findBookPortOut;
    private final FeatureTogglePortOut featureTogglePortOut;

    public FindBookUseCase(FindBookPortOut findBookPortOut,
                           FeatureTogglePortOut featureTogglePortOut) {
        this.findBookPortOut = findBookPortOut;
        this.featureTogglePortOut = featureTogglePortOut;
    }

    @Override
    public Book findIsbn(String isbn) {

        if (featureTogglePortOut.isEnabled("test-ff")) {
            throw new RuntimeException("feature toggle test enabled");
        }

        return findBookPortOut.findIsbn(isbn).orElseThrow(() -> new RuntimeException("Book not Found"));
    }

}