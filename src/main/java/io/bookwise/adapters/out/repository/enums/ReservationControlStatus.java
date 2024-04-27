package io.bookwise.adapters.out.repository.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum ReservationControlStatus {

    PENDING         (1, "PENDING", "The reservation is pending"),
    CONFIRMED       (2, "CONFIRMED", "The reservation is confirmed"),
    CANCELLED       (3, "CANCELLED", "The reservation is cancelled"),
    COMPLETED       (4, "COMPLETED", "The reservation is completed"),
    ERROR           (5, "ERROR", "The reservation has an error");

    private final int code;
    private final String reason;
    private final String info;

    public static ReservationControlStatus get(String reason) {
        return Arrays.stream(values())
                .filter(reservationControlStatus -> reservationControlStatus.getReason().equals(reason))
                .findFirst()
                .orElseThrow();
    }

    public static boolean equals(ReservationControlStatus expected, ReservationControlStatus actual) {
        return expected.getCode() == actual.getCode();
    }

}