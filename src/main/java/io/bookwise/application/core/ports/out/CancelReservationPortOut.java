package io.bookwise.application.core.ports.out;

import io.bookwise.application.core.dto.CancelReservationRequest;

public interface CancelReservationPortOut {
    void execute(CancelReservationRequest reserve);
}