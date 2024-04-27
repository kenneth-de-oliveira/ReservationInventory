package io.bookwise.adapters.out.repository.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.bookwise.adapters.out.repository.entity.ReservationControlEntity;

import java.util.UUID;

public record ReservationQueue(@JsonProperty("process_id") UUID id) {
    public static ReservationQueue toReservationQueue(ReservationControlEntity reservationControlEntity) {
        return new ReservationQueue(reservationControlEntity.getId());
    }
}