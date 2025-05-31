package io.bookwise.adapters.out;

import io.bookwise.adapters.out.pub.ReservationMessageQueuePublisher;
import io.bookwise.adapters.out.repository.dto.ReservationQueue;
import io.bookwise.application.core.ports.out.ReservationMessageQueuePublisherPortOut;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReservationMessageQueuePublisherAdapterOut implements ReservationMessageQueuePublisherPortOut {

    private final ReservationMessageQueuePublisher reservationMessageQueuePublisher;

    @Override
    public ReservationQueue sendToQueueRequest(String isbn, String document) {
        log.info("Sending message to queue with isbn: {} and document: {}", isbn, document);
        return reservationMessageQueuePublisher.sendToQueueRequest(isbn, document);
    }

}