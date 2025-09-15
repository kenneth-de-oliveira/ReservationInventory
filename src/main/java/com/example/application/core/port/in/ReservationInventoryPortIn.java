package com.example.application.core.port.in;

import com.example.adapter.out.repository.dto.ReserveInfo;
import com.example.adapter.out.repository.dto.ReservationQueue;
import com.example.application.core.domain.Reservation;

import java.util.List;

public interface ReservationInventoryPortIn {
    ReservationQueue enqueueReservationRequest(String isbn, String document);
    void reserve(Reservation reservation);
    List<ReserveInfo> findAllByDocument(String document);
}