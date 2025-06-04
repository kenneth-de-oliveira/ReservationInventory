package io.bookwise.application.core.dto;

public record CancelReservation(
        String isbn,
        String document,
        CancelReservationAction action
) {

}