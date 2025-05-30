package io.bookwise.application.core.ports.out;

import io.bookwise.adapters.out.repository.dto.ReservationQueue;

public interface ReservationMessageQueuePublisherPortOut {
    ReservationQueue sendToQueueRequest(String isbn, String document);
}