package com.example.application.core.port.out;

import com.example.adapter.out.repository.dto.ReservationQueue;

public interface ReservationMessageQueuePublisherPortOut {
    ReservationQueue sendToQueueRequest(String isbn, String document);
}