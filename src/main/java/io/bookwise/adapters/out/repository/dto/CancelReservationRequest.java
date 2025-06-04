package io.bookwise.adapters.out.repository.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CancelReservationRequest(
        String isbn,
        String document,
        @JsonProperty("action")
        CancelReservationAction cancelReservationAction
) {
}