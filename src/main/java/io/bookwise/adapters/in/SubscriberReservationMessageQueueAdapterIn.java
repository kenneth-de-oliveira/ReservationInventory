package io.bookwise.adapters.in;

import io.bookwise.application.core.ports.in.ReservationInventoryPortIn;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import static io.bookwise.adapters.out.mapper.ReservationInventoryMapper.toDomain;

@Slf4j
@Component
@RequiredArgsConstructor
public class SubscriberReservationMessageQueueAdapterIn {

    private final ReservationInventoryPortIn reservationInventoryPortIn;

    @RabbitListener(queues = "${mq.queues.reservationInventory}")
    private void receiveMessageQueue(@Payload String payload) {
        try {
            log.info("Received message queue: {}", payload);
            reservationInventoryPortIn.reserve( toDomain(payload) );
            log.info("Reservation created");
        } catch (Exception ex) {
            log.error("Error received message queue: {}", ex.getMessage());
            throw new RuntimeException(ex.getMessage(), ex);
        }
    }

}