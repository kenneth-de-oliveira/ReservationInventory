package com.example.adapter.out.repository.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.example.adapter.out.repository.entity.ReservationControlEntity;

import java.util.UUID;

public record ReservationQueue(@JsonProperty("process_id") UUID id) {
    public static ReservationQueue toReservationQueue(ReservationControlEntity reservationControlEntity) {
        return new ReservationQueue(reservationControlEntity.getId());
    }
}