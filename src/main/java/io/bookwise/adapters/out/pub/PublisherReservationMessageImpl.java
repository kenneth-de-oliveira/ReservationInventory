package io.bookwise.adapters.out.pub;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.bookwise.adapters.out.mapper.ReservationInventoryMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import org.springframework.amqp.core.Queue;

@Slf4j
@Component
@RequiredArgsConstructor
public class PublisherReservationMessageImpl {

    private final RabbitTemplate rabbitTemplate;
    private final Queue queue;

    public void send(String isbn, String document) {
        sendToQueue(isbn, document);
    }

    private void sendToQueue(String isbn, String document) {
        try {
            var messageToQueue = convertIntoJson(isbn, document);
            rabbitTemplate.convertAndSend(queue.getName(), messageToQueue);
            log.info("Message sent to queue: {}", messageToQueue);
        } catch (Exception ex) {
            log.error("Error sending message to queue: {}", ex.getMessage());
            throw new RuntimeException("Error sending message to queue", ex);
        }
    }

    private String convertIntoJson(String isbn, String document) {
        try {
            log.info("Converting into json: isbn: {} and document: {}", isbn, document);
            var mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(
                    ReservationInventoryMapper.toRequest(isbn, document)
            );
            log.info("Converted into json: isbn: {} and document: {}", isbn, document);
            return json;
        } catch (JsonProcessingException ex) {
            log.error("Error converting into json: isbn: {} and document: {}", isbn, document);
            throw new RuntimeException(ex.getMessage(), ex);
        }
    }

}