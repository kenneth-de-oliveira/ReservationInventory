package io.bookwise.application.core.ports.in;

import io.bookwise.adapters.out.repository.dto.ReserveInfo;
import io.bookwise.adapters.out.repository.dto.ReservationQueue;
import io.bookwise.application.core.domain.Reservation;

import java.util.List;

public interface ReservationInventoryPortIn {
    ReservationQueue enqueueReservationRequest(String isbn, String document);
    void reserve(Reservation reservation);
    List<ReserveInfo> findAllByDocument(String document);
}