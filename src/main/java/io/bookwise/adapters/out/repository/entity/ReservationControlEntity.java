package io.bookwise.adapters.out.repository.entity;

import io.bookwise.adapters.out.repository.enums.ReservationControlStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@Entity(name = "RESERVATION_CONTROL")
@AllArgsConstructor
@NoArgsConstructor
public class ReservationControlEntity {

    @Id
    private UUID id;

    private String isbn;

    private String document;

    @Enumerated(EnumType.STRING)
    private ReservationControlStatus status;

    private String errorDescription;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        this.id = UUID.randomUUID();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

}