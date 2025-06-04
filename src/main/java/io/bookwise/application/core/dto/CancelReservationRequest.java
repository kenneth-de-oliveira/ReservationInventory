package io.bookwise.application.core.dto;

public record CancelReservationRequest(
        String isbn,
        String document,
        CancelReservationAction action
) {

}