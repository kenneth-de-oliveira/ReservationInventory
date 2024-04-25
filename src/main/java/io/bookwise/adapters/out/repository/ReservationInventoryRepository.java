package io.bookwise.adapters.out.repository;

import io.bookwise.adapters.out.repository.entity.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationInventoryRepository extends JpaRepository<ReservationEntity, Long> {

}
