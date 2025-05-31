package io.bookwise.application.core.ports.out;

import io.bookwise.adapters.out.repository.dto.CancelReservationRequest;

public interface CancelReservationPortOut {
    void cancel(CancelReservationRequest reserve);
}