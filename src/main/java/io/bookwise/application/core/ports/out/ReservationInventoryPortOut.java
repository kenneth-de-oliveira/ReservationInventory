package io.bookwise.application.core.ports.out;

import io.bookwise.adapters.out.repository.projection.ReservationProjection;
import io.bookwise.application.core.domain.Reservation;

import java.util.List;

public interface ReservationInventoryPortOut {
    void init(Reservation reservation);
    List<ReservationProjection> find(String document);
}