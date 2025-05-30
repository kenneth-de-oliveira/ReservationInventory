package io.bookwise.adapters.out.pub;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.bookwise.adapters.out.repository.ReservationControlRepository;
import io.bookwise.adapters.out.repository.dto.ReservationQueue;
import io.bookwise.adapters.out.repository.entity.ReservationControlEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Stream;

import static io.bookwise.adapters.out.mapper.ReservationInventoryMapper.toRequest;
import static io.bookwise.adapters.out.repository.dto.ReservationQueue.toReservationQueue;
import static io.bookwise.adapters.out.repository.enums.ReservationControlStatus.PENDING;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReservationMessageQueuePublisher {

    private final RabbitTemplate rabbitTemplate;
    private final ReservationControlRepository reservationControlRepository;
    private final Queue queue;
    private final ObjectMapper objectMapper;

    public ReservationQueue sendToQueueRequest(String isbn, String document) {
        return Stream.ofNullable(isbn)
                .filter(isbnValue -> Objects.nonNull(document))
                .map(isbnValue -> this.processReservation(isbnValue, document))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("ISBN or Document is null"));
    }

    private ReservationQueue processReservation(String isbn, String document) {
        return Stream.ofNullable(isbn)
                .filter(isbnValue -> Objects.nonNull(document))
                .map(isbnValue -> {
                    var messageJson = buildReservationJsonMessage(isbnValue, document);
                    var reservationQueue = saveReservationControl(isbnValue, document);
                    rabbitTemplate.convertAndSend(queue.getName(), messageJson);
                    log.info("Message sent to queue with status: {}", PENDING);
                    return reservationQueue;
                })
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("ISBN or Document is null"));
    }

    private ReservationQueue saveReservationControl(String isbn, String document) {
        return Stream.ofNullable(isbn)
                .filter(isbnValue -> Objects.nonNull(document))
                .map(isbnValue -> {
                    var entity = ReservationControlEntity.builder()
                            .isbn(isbnValue)
                            .document(document)
                            .status(PENDING)
                            .build();
                    reservationControlRepository.save(entity);
                    log.info("Reservation control repository saved with id: {}", entity.getId());
                    return toReservationQueue(entity);
                })
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("ISBN or Document is null"));
    }

    private String buildReservationJsonMessage(String isbn, String document) {
        return Stream.ofNullable(isbn)
                .filter(isbnValue -> Objects.nonNull(document))
                .map(isbnValue -> toRequest(isbnValue, document))
                .map(request -> {
                    try {
                        return objectMapper.writeValueAsString(request);
                    } catch (JsonProcessingException ex) {
                        log.error("Error converting to JSON: isbn: {} and document: {}", isbn, document, ex);
                        throw new RuntimeException("Error converting to JSON", ex);
                    }
                })
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("ISBN or Document is null"));
    }

}