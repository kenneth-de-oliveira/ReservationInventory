package io.bookwise.application.core.ports.in;

public interface SubscriberReservationMessageToQueuePortIn {
    void receive(String isbn, String document);
}
