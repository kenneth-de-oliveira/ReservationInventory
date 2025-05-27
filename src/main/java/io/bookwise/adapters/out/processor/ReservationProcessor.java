package io.bookwise.adapters.out.processor;

import io.bookwise.adapters.out.repository.entity.ReservationControlEntity;
import io.bookwise.adapters.out.repository.enums.ReservationControlStatus;
import io.bookwise.application.core.domain.Reservation;

public abstract class ReservationProcessor {

    public abstract boolean supportsStatus(ReservationControlStatus reservationControlStatus);

    public abstract void process(Reservation reservation, ReservationControlEntity reservationControlEntity);

}