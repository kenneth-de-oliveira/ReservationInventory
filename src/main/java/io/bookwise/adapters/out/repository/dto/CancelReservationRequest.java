package io.bookwise.adapters.out.repository.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CancelReservationRequest(
        String isbn,
        @JsonProperty("action")
        CancelReservationAction cancelReservationAction
) {
}