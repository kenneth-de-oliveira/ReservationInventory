package io.bookwise.adapters.out.pub;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.bookwise.adapters.out.mapper.ReservationInventoryMapper;
import io.bookwise.adapters.out.repository.ReservationControlRepository;
import io.bookwise.adapters.out.repository.dto.ReservationQueue;
import io.bookwise.adapters.out.repository.entity.ReservationControlEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import static io.bookwise.adapters.out.repository.dto.ReservationQueue.toReservationQueue;
import static io.bookwise.adapters.out.repository.enums.ReservationControlStatus.PENDING;

@Slf4j
@Component
@RequiredArgsConstructor
public class PublisherReservationMessageClient {

    private final RabbitTemplate rabbitTemplate;
    private final ReservationControlRepository reservationControlRepository;
    private final Queue queue;

    public ReservationQueue send(String isbn, String document) {
        return sendToQueue(isbn, document);
    }

    private ReservationQueue sendToQueue(String isbn, String document) {
        try {
            return init(isbn, document);
        } catch (Exception ex) {
            log.error("Error sending message to queue: {}", ex.getMessage());
            throw new RuntimeException("Error sending message to queue", ex);
        }
    }

    private ReservationQueue init(String isbn, String document) {
        var messageToQueue = convertIntoJson(isbn, document);
        var reservationQueue = sendReservationControlRepository(isbn, document);
        rabbitTemplate.convertAndSend(queue.getName(), messageToQueue);
        log.info("Message sent to queue with status: {}", PENDING);
        return reservationQueue;
    }

    private ReservationQueue sendReservationControlRepository(String isbn, String document) {
        log.info("Saving reservation control repository");
        var reservationControlEntity = new ReservationControlEntity();
        reservationControlEntity.setIsbn(isbn);
        reservationControlEntity.setDocument(document);
        reservationControlEntity.setStatus(PENDING);
        reservationControlRepository.save(reservationControlEntity);
        log.info("Reservation control repository saved with id: {}", reservationControlEntity.getId());
        return toReservationQueue(reservationControlEntity);
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