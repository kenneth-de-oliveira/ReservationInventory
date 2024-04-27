package io.bookwise.application.core.ports.in;

import io.bookwise.adapters.out.repository.dto.ReservationProjection;
import io.bookwise.adapters.out.repository.dto.ReservationQueue;
import io.bookwise.application.core.domain.Reservation;

import java.util.List;

public interface ReservationInventoryPortIn {
    ReservationQueue sendToReservationQueue(String isbn, String document);
    void init(Reservation reservation);
    List<ReservationProjection> find(String document);
}