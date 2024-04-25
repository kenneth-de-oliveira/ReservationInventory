package io.bookwise.adapters.out;

import io.bookwise.adapters.out.pub.PublisherReservationMessageImpl;
import io.bookwise.application.core.ports.out.PublishReservationMessageToQueuePortOut;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class PublishReservationMessageToQueueAdapterOut implements PublishReservationMessageToQueuePortOut {

    private final PublisherReservationMessageImpl publisherReservationMessageImpl;

    @Override
    public void send(String isbn, String document) {
        log.info("Sending message to queue with isbn: {} and document: {}", isbn, document);
        publisherReservationMessageImpl.send(isbn, document);
    }

}