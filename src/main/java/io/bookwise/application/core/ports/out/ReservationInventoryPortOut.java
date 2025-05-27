package io.bookwise.application.core.ports.out;

import io.bookwise.adapters.out.repository.dto.ReserveInfo;
import io.bookwise.application.core.domain.Reservation;

import java.util.List;

public interface ReservationInventoryPortOut {
    void execute(Reservation reservation);
    List<ReserveInfo> findAllByDocument(String document);
    Boolean checkIfBookIsReservedByIsbn(String isbn);
}