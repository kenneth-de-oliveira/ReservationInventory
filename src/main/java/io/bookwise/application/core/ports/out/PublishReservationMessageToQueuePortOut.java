package io.bookwise.application.core.ports.out;

public interface PublishReservationMessageToQueuePortOut {
    void send(String isbn, String document);
}