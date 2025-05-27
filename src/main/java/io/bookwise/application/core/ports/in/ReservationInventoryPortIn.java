package io.bookwise.application.core.ports.in;

import io.bookwise.adapters.out.repository.dto.ReserveInfo;
import io.bookwise.adapters.out.repository.dto.ReservationQueue;

import java.util.List;

public interface ReservationInventoryPortIn {
    ReservationQueue sendToReservationQueue(String isbn, String document);
    void init(io.bookwise.application.core.domain.Reservation reservation);
    List<ReserveInfo> findAllByDocument(String document);
}