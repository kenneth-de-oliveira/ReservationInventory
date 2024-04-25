package io.bookwise.adapters.in;

import io.bookwise.application.core.ports.in.ReservationInventoryPortIn;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/reservation-inventory")
@RequiredArgsConstructor
public class ReservationInventoryAdapterIn {

    private final ReservationInventoryPortIn reservationInventoryPortIn;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void reservation(@RequestParam String isbn, @RequestParam String document) {
        log.info("Reservation received: isbn: {} and document: {}", isbn, document);
        reservationInventoryPortIn.sendToReservationQueue(isbn, document);
        log.info("Reservation created");
    }

}