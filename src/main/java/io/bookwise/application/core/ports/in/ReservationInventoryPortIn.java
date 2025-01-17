package io.bookwise.application.core.ports.in;

import io.bookwise.adapters.out.repository.dto.Reservation;
import io.bookwise.adapters.out.repository.dto.ReservationQueue;

import java.util.List;

public interface ReservationInventoryPortIn {
    ReservationQueue sendToReservationQueue(String isbn, String document);
    void init(io.bookwise.application.core.domain.Reservation reservation);
    List<Reservation> findAllByDocument(String document);
}