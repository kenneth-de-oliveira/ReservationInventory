package io.bookwise.adapters.out.repository;

import io.bookwise.adapters.out.repository.entity.ReservationControlEntity;
import io.bookwise.adapters.out.repository.enums.ReservationControlStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReservationControlRepository extends JpaRepository<ReservationControlEntity, Long> {
    Optional<ReservationControlEntity> findByIsbnAndStatus(String isbn, ReservationControlStatus status);
    List<ReservationControlEntity> findByStatus(ReservationControlStatus reservationControlStatus);
}