package io.bookwise.application.core.ports.in;

import io.bookwise.application.core.domain.Reservation;

public interface CancelReservationPortIn {
    void cancel(Reservation reservation);
}