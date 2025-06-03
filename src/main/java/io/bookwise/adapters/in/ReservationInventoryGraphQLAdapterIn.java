package io.bookwise.adapters.in;

import io.bookwise.application.core.ports.in.ReservationInventoryPortIn;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ReservationInventoryGraphQLAdapterIn {

    private final ReservationInventoryPortIn reservationInventoryPortIn;

    @MutationMapping
    public String reservation(@Argument String isbn, @Argument String document) {
        log.info("Reservation with isbn: {} and document: {}", isbn, document);
        var reservationQueue = reservationInventoryPortIn.enqueueReservationRequest(isbn, document);
        log.info("Reservation enqueued: {}", reservationQueue.id());
        return reservationQueue.id().toString();
    }

}