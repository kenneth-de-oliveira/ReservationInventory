package io.bookwise.application.core.ports.out;

import io.bookwise.application.core.domain.Reservation;

public interface CancelReservationPortOut {
    void execute(Reservation reservation);
}