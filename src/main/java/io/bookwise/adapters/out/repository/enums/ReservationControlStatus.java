package io.bookwise.adapters.out.repository.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

@Getter
@AllArgsConstructor
public enum ReservationControlStatus {

    PENDING             (1, "PENDING", "The reservation is pending"),
    CONFIRMED           (2, "CONFIRMED", "The reservation is confirmed"),
    CANCELLED           (3, "CANCELLED", "The reservation is cancelled"),
    CANCELLED_REQUEST   (4, "CANCELLED_REQUEST", "The reservation cancellation is requested"),
    COMPLETED           (5, "COMPLETED", "The reservation is completed"),
    ERROR               (6, "ERROR", "The reservation has an error");

    private final int code;
    private final String reason;
    private final String info;

    public static Optional<ReservationControlStatus> get(String reason) {
        return Optional.ofNullable(Arrays.stream(values())
                .filter(reservationControlStatus -> reservationControlStatus.getReason().equals(reason))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid reservation status : " + reason)));
    }

    public static boolean equals(ReservationControlStatus expected, ReservationControlStatus actual) {
        return expected.getCode() == actual.getCode();
    }

}