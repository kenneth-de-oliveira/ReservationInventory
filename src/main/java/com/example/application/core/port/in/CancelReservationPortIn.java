package com.example.application.core.port.in;

import com.example.application.core.domain.Reservation;

public interface CancelReservationPortIn {
    void cancel(Reservation reservation);
}