package io.bookwise.application.usecase;

import io.bookwise.adapters.out.repository.dto.CancelReservationRequest;
import io.bookwise.application.core.ports.in.CancelReservationPortIn;
import io.bookwise.application.core.ports.out.CancelReservationPortOut;

public class CancelReservationUseCase implements CancelReservationPortIn {

    private final CancelReservationPortOut cancelReservationPortOut;

    public CancelReservationUseCase(CancelReservationPortOut cancelReservationPortOut) {
        this.cancelReservationPortOut = cancelReservationPortOut;
    }

    @Override
    public void cancel(CancelReservationRequest reserve) {
        cancelReservationPortOut.cancel(reserve);
    }

}