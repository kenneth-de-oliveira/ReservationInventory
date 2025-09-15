package com.example.application.core.port.out;

import com.example.adapter.out.repository.dto.ReserveInfo;
import com.example.application.core.domain.Reservation;

import java.util.List;

public interface ReservationInventoryPortOut {
    void execute(Reservation reservation);
    List<ReserveInfo> findAllByDocument(String document);
    Boolean checkIfBookIsReservedByIsbn(String isbn);
    Boolean checkIfBookIsReservedByIsbnAndDocument(String isbn, String document);
}