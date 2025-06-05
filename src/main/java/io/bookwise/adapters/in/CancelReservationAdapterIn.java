package io.bookwise.adapters.in;

import io.bookwise.application.core.domain.Reservation;
import io.bookwise.application.core.ports.in.CancelReservationPortIn;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/cancel-reservation")
@RequiredArgsConstructor
public class CancelReservationAdapterIn {

    private final CancelReservationPortIn cancelReservationPortIn;

    @PatchMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancel(@RequestBody Reservation reservation) {
        log.info("Cancel reservation request for ISBN: {}", reservation.getIsbn());
        cancelReservationPortIn.cancel(reservation);
        log.info("Reservation cancellation request for ISBN: {}", reservation.getIsbn());
    }

}