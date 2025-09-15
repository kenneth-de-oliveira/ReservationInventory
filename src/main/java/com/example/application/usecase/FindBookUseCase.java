package com.example.application.usecase;

import com.example.application.core.domain.Book;
import com.example.application.core.port.in.FindBookPortIn;
import com.example.application.core.port.out.FeatureTogglePortOut;
import com.example.application.core.port.out.FindBookPortOut;
import com.example.application.core.port.out.ReservationInventoryPortOut;

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

        return findBookPortOut.findIsbn(isbn).stream()
                .peek(book -> book.setReserved(reservationInventoryPortOut.checkIfBookIsReservedByIsbn(isbn)))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Book not found"));
    }

    @Override
    public List<Book> findAll() {
        return findBookPortOut.findAll().stream()
                .peek(book -> book.setReserved(reservationInventoryPortOut.checkIfBookIsReservedByIsbn(book.getIsbn())))
                .toList();
    }

}