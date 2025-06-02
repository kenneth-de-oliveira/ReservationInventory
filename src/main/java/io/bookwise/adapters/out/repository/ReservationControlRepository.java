package io.bookwise.adapters.out.repository;

import io.bookwise.adapters.out.repository.entity.ReservationControlEntity;
import io.bookwise.adapters.out.repository.enums.ReservationControlStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ReservationControlRepository extends JpaRepository<ReservationControlEntity, UUID> {
    Optional<ReservationControlEntity> findByIsbnAndStatus(String isbn, ReservationControlStatus status);
}