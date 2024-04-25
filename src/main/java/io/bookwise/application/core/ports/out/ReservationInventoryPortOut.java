package io.bookwise.application.core.ports.out;

import io.bookwise.application.core.domain.Reservation;

public interface ReservationInventoryPortOut {
    void init(Reservation reservation);
}