package io.bookwise.adapters.out;

import io.bookwise.adapters.out.pub.PublisherReservationMessageClient;
import io.bookwise.adapters.out.repository.dto.ReservationQueue;
import io.bookwise.application.core.ports.out.PublishReservationMessageToQueuePortOut;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PublishReservationMessageToQueueAdapterOut implements PublishReservationMessageToQueuePortOut {

    private final PublisherReservationMessageClient publisherReservationMessageClient;

    @Override
    public ReservationQueue send(String isbn, String document) {
        log.info("Sending message to queue with isbn: {} and document: {}", isbn, document);
        return publisherReservationMessageClient.send(isbn, document);
    }

}