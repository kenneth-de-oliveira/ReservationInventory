package io.bookwise.adapters.out;

import io.bookwise.adapters.out.repository.ReservationControlRepository;
import io.bookwise.adapters.out.repository.ReservationInventoryRepository;
import io.bookwise.adapters.out.repository.dto.ReservationProjection;
import io.bookwise.adapters.out.repository.entity.ReservationControlEntity;
import io.bookwise.adapters.out.repository.enums.ReservationControlStatus;
import io.bookwise.application.core.domain.Reservation;
import io.bookwise.application.core.ports.out.ReservationInventoryPortOut;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

import static io.bookwise.adapters.out.mapper.ReservationInventoryMapper.toEntity;
import static io.bookwise.adapters.out.repository.enums.ReservationControlStatus.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReservationInventoryAdapterOut implements ReservationInventoryPortOut {

    private final ReservationInventoryRepository repository;
    private final ReservationControlRepository reservationControlRepository;

    @Override
    public void init(Reservation reservation) {
        log.info("Creating reservation for book with ISBN: {} for student with document: {}", reservation.getIsbn(), reservation.getDocument());
        this.run(reservation);
        log.info("Reservation created successfully with status {}", CONFIRMED);
    }

    private void run(Reservation reservation) {
        var reservationControlEntity = reservationControlRepository.findByIsbnAndStatus(reservation.getIsbn(), PENDING);
        reservationControlEntity.ifPresent(control -> this.processReservation(reservation, control));
    }

    private void processReservation(Reservation reservation, ReservationControlEntity reservationControlEntity) {
        var status = reservationControlEntity.getStatus();
        if (ReservationControlStatus.equals(status, PENDING)) {
            saveReservationControlRepository(reservationControlEntity, CONFIRMED);
            reserve(reservation);
        } else {
           saveReservationControlRepository(reservationControlEntity, ERROR);
        }
    }

    private void reserve(Reservation reservation) {
        var entity = toEntity(reservation);
        repository.save(entity);
    }

    private void saveReservationControlRepository(ReservationControlEntity reservationControlEntity, ReservationControlStatus status) {
        reservationControlEntity.setStatus(status);
        reservationControlRepository.save(reservationControlEntity);
    }

    @Override
    public List<ReservationProjection> find(String document) {
        var reservations = repository.find(document, ReservationProjection.class);
        log.info("Reservations found: {}", reservations.size());
        return reservations;
    }

}