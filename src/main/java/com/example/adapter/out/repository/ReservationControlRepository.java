package com.example.adapter.out.repository;

import com.example.adapter.out.repository.entity.ReservationControlEntity;
import com.example.adapter.out.repository.enums.ReservationControlStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReservationControlRepository extends JpaRepository<ReservationControlEntity, UUID> {
    Optional<ReservationControlEntity> findByIsbnAndStatus(String isbn, ReservationControlStatus status);
    List<ReservationControlEntity> findByStatus(ReservationControlStatus reservationControlStatus);
}