package com.example.adapter.in;

import com.example.adapter.out.repository.dto.ReserveInfo;
import com.example.adapter.out.repository.dto.ReservationQueue;
import com.example.application.core.port.in.ReservationInventoryPortIn;
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
    public ReservationQueue reservation(@RequestParam String isbn, @RequestParam String document) {
        log.info("Reservation with isbn: {} and document: {}", isbn, document);
        var reservationQueue = reservationInventoryPortIn.enqueueReservationRequest(isbn, document);
        log.info("Reservation enqueued: {}", reservationQueue.id());
        return reservationQueue;
    }

    @GetMapping("/reservations")
    public List<ReserveInfo> findAllByDocument(@RequestParam String document) {
        log.info("Find reservation List by document: {}", document);
        return reservationInventoryPortIn.findAllByDocument(document);
    }

}