package io.bookwise.application.core.ports.out;

import io.bookwise.application.core.dto.CancelReservation;

public interface CancelReservationPortOut {
    void execute(CancelReservation reserve);
}