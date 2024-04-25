package io.bookwise.application.core.ports.in;

import io.bookwise.application.core.domain.Reservation;

public interface ReservationInventoryPortIn {
    void sendToReservationQueue(String isbn, String document);
    void init(Reservation reservation);
}