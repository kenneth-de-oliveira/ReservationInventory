package io.bookwise.adapters.out;

import io.bookwise.adapters.out.repository.ReservationInventoryRepository;
import io.bookwise.adapters.out.repository.projection.ReservationProjection;
import io.bookwise.application.core.domain.Reservation;
import io.bookwise.application.core.ports.out.ReservationInventoryPortOut;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

import static io.bookwise.adapters.out.mapper.ReservationInventoryMapper.toEntity;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReservationInventoryAdapterOut implements ReservationInventoryPortOut {

    private final ReservationInventoryRepository repository;

    @Override
    public void init(Reservation reservation) {
        log.info("Creating reservation for book with ISBN: {} for student with document: {}", reservation.getIsbn(), reservation.getDocument());
        var entity = toEntity(reservation);
        repository.save(entity);
        log.info("Reservation created successfully");
    }

    @Override
    public List<ReservationProjection> find(String document) {
        var reservations = repository.find(document);
        log.info("Reservations found: {}", reservations.size());
        return reservations;
    }

}