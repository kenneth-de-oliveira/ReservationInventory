package io.bookwise.application.core.ports.in;

import io.bookwise.adapters.out.repository.dto.CancelReservationRequest;

public interface CancelReservationPortIn {
    void cancel(CancelReservationRequest reserve);
}