package com.example.application.core.port.out;

import com.example.application.core.domain.Reservation;

public interface CancelReservationPortOut {
    void execute(Reservation reservation);
}