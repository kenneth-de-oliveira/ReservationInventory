package io.bookwise.application.core.ports.in;

import io.bookwise.application.core.dto.CancelReservationRequest;

public interface CancelReservationPortIn {
    void cancel(CancelReservationRequest reserve);
}