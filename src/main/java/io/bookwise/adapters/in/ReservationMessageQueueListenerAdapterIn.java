package io.bookwise.adapters.in;

import io.bookwise.adapters.out.mapper.ReservationInventoryMapper;
import io.bookwise.application.core.ports.in.ReservationInventoryPortIn;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReservationMessageQueueListenerAdapterIn {

    private final ReservationInventoryPortIn reservationInventoryPortIn;
    private final ReservationInventoryMapper mapper;

    @RabbitListener(queues = "${mq.queues.reservationInventory}")
    private void receiveMessageQueue(@Payload String payload) {
        try {
            log.info("Received message queue: {}", payload);
            reservationInventoryPortIn.reserve( mapper.toDomain(payload) );
            log.info("Reservation created");
        } catch (Exception ex) {
            log.error("Error received message queue: {}", ex.getMessage());
            throw new RuntimeException(ex.getMessage(), ex);
        }
    }

}