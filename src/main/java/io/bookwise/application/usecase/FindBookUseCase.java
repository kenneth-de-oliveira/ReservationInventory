package io.bookwise.application.usecase;

import io.bookwise.application.core.domain.Book;
import io.bookwise.application.core.ports.in.FindBookPortIn;
import io.bookwise.application.core.ports.out.FeatureTogglePortOut;
import io.bookwise.application.core.ports.out.FindBookPortOut;
import io.bookwise.application.core.ports.out.ReservationInventoryPortOut;

import java.util.List;

public class FindBookUseCase implements FindBookPortIn {

    private final FindBookPortOut findBookPortOut;
    private final ReservationInventoryPortOut reservationInventoryPortOut;
    private final FeatureTogglePortOut featureTogglePortOut;

    public FindBookUseCase(FindBookPortOut findBookPortOut,
                           ReservationInventoryPortOut reservationInventoryPortOut,
                           FeatureTogglePortOut featureTogglePortOut) {
        this.findBookPortOut = findBookPortOut;
        this.reservationInventoryPortOut = reservationInventoryPortOut;
        this.featureTogglePortOut = featureTogglePortOut;
    }

    @Override
    public Book findIsbn(String isbn) {

        if (featureTogglePortOut.isEnabled("test-ff")) {
            throw new RuntimeException("feature toggle test enabled");
        }
        return findBookPortOut.findIsbn(isbn).map(book -> {
            book.setReserved(reservationInventoryPortOut.checkIfBookIsReservedByIsbn(isbn));
            return book;
        }).orElseThrow(() -> new RuntimeException("Book not found"));

    }

    @Override
    public List<Book> findAll() {
        return findBookPortOut.findAll().stream()
                .peek(book -> book.setReserved(reservationInventoryPortOut.checkIfBookIsReservedByIsbn(book.getIsbn())))
                .toList();
    }

}