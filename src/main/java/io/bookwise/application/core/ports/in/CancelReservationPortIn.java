package io.bookwise.application.core.ports.in;

import io.bookwise.application.core.dto.CancelReservation;

public interface CancelReservationPortIn {
    void cancel(CancelReservation reserve);
}