package io.bookwise.application.core.enums;

import java.util.Arrays;
import java.util.Optional;

public enum ReservationStatus {

    PENDING             (1, "PENDING", "The reservation is pending"),
    CONFIRMED           (2, "CONFIRMED", "The reservation is confirmed"),
    CANCELLED           (3, "CANCELLED", "The reservation is cancelled"),
    CANCELLED_REQUEST   (4, "CANCELLED_REQUEST", "The reservation cancellation is requested"),
    COMPLETED           (5, "COMPLETED", "The reservation is completed"),
    ERROR               (6, "ERROR", "The reservation has an error");

    private final int code;
    private final String reason;
    private final String info;

    ReservationStatus(int code, String reason, String info) {
        this.code = code;
        this.reason = reason;
        this.info = info;
    }

    public static Optional<ReservationStatus> get(String reason) {
        return Optional.ofNullable(Arrays.stream(values())
                .filter(reservationControlStatus -> reservationControlStatus.getReason().equals(reason))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid reservation status : " + reason)));
    }

    public int getCode() {
        return code;
    }

    public String getReason() {
        return reason;
    }

    public String getInfo() {
        return info;
    }

}