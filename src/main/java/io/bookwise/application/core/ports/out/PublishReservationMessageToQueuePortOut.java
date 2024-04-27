package io.bookwise.application.core.ports.out;

import io.bookwise.adapters.out.repository.dto.ReservationQueue;

public interface PublishReservationMessageToQueuePortOut {
    ReservationQueue send(String isbn, String document);
}