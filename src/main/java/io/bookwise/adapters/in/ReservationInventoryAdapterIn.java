package io.bookwise.adapters.in;

import io.bookwise.adapters.out.repository.projection.ReservationProjection;
import io.bookwise.application.core.ports.in.ReservationInventoryPortIn;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/reservations")
    public List<ReservationProjection> find(@RequestParam String document) {
        log.info("Find reservation List by document: {}", document);
        return reservationInventoryPortIn.find(document);
    }

}