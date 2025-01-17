package io.bookwise.adapters.out;

import io.bookwise.adapters.out.client.InventoryManagementClient;
import io.bookwise.adapters.out.mapper.InventoryManagementMapper;
import io.bookwise.adapters.out.repository.ReservationControlRepository;
import io.bookwise.adapters.out.repository.ReservationInventoryRepository;
import io.bookwise.adapters.out.repository.dto.Reservation;
import io.bookwise.adapters.out.repository.entity.ReservationControlEntity;
import io.bookwise.adapters.out.repository.enums.ReservationControlStatus;
import io.bookwise.application.core.ports.out.ReservationInventoryPortOut;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static io.bookwise.adapters.out.mapper.ReservationInventoryMapper.toEntity;
import static io.bookwise.adapters.out.repository.enums.ReservationControlStatus.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReservationInventoryAdapterOut implements ReservationInventoryPortOut {

    private final ReservationInventoryRepository repository;
    private final ReservationControlRepository reservationControlRepository;
    private final InventoryManagementClient inventoryManagementClient;
    private final InventoryManagementMapper inventoryManagementMapper;

    @Override
    public void init(io.bookwise.application.core.domain.Reservation reservation) {
        log.info("Creating reservation for book with ISBN: {} for student with document: {}", reservation.getIsbn(), reservation.getDocument());
        this.run(reservation);
        log.info("Reservation created successfully with status {}", CONFIRMED);
    }

    private void run(io.bookwise.application.core.domain.Reservation reservation) {
        var reservationControlEntity = reservationControlRepository.findByIsbnAndStatus(reservation.getIsbn(), PENDING);
        reservationControlEntity.ifPresent(control -> this.processReservation(reservation, control));
    }

    private void processReservation(io.bookwise.application.core.domain.Reservation reservation, ReservationControlEntity reservationControlEntity) {
        var status = reservationControlEntity.getStatus();
        if (ReservationControlStatus.equals(status, PENDING)) {
            saveReservationControlRepository(reservationControlEntity, CONFIRMED);
            reserve(reservation);
        } else {
           saveReservationControlRepository(reservationControlEntity, ERROR);
        }
    }

    private void reserve(io.bookwise.application.core.domain.Reservation reservation) {
        var entity = toEntity(reservation);
        repository.save(entity);
    }

    private void saveReservationControlRepository(ReservationControlEntity reservationControlEntity, ReservationControlStatus status) {
        reservationControlEntity.setStatus(status);
        reservationControlRepository.save(reservationControlEntity);
    }

    @Override
    public List<Reservation> findAllByDocument(String document) {
        var reservationEntities = Optional.ofNullable(repository.findByDocument(document))
                .orElseGet(() -> {
                    log.info("No reservationEntities found for student with document: {}", document);
                    return Collections.emptyList();
                });

        var reservations = reservationEntities.stream()
                .map(entity -> {
                    var request = inventoryManagementMapper.mapToSearchBookRequest(entity.getIsbn());
                    var response = inventoryManagementClient.findByIsbn(request);
                    var book = inventoryManagementMapper.mapToBookDomain(response);
                    return new Reservation(book.getTitle(), book.getAuthorName(), book.getIsbn());
                })
                .collect(Collectors.toList());

        log.info("Reservations found: {}", reservations.size());
        return reservations;
    }

    @Override
    public Boolean checkIfBookIsReservedByIsbn(String isbn) {
        var isReserved = repository.existsByIsbn(isbn);
        log.info("Book with ISBN: {} is reserved: {}", isbn, isReserved);
        return isReserved;
    }

}