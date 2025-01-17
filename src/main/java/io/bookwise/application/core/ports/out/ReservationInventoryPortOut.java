package io.bookwise.application.core.ports.out;

import io.bookwise.adapters.out.repository.dto.Reservation;

import java.util.List;

public interface ReservationInventoryPortOut {
    void init(io.bookwise.application.core.domain.Reservation reservation);
    List<Reservation> findAllByDocument(String document);
    Boolean checkIfBookIsReservedByIsbn(String isbn);
}