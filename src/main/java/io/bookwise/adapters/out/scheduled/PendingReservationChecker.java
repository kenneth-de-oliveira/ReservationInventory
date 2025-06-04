package io.bookwise.adapters.out.scheduled;

import io.bookwise.adapters.out.FindStudentAdapterOut;
import io.bookwise.adapters.out.ReservationInventoryAdapterOut;
import io.bookwise.adapters.out.SmtpMailMessageAdapterOut;
import io.bookwise.application.core.dto.MailMessage;
import io.bookwise.adapters.out.mapper.ReservationInventoryMapper;
import io.bookwise.adapters.out.repository.ReservationControlRepository;
import io.bookwise.adapters.out.repository.entity.ReservationControlEntity;
import io.bookwise.application.core.domain.Reservation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

import static io.bookwise.adapters.out.repository.enums.ReservationControlStatus.*;

/**
 * Scheduled task to process pending reservations every day at midnight.
 * It retrieves all pending reservations, processes them, and sends confirmation emails.
 *
 * @author kenneth
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class PendingReservationChecker {

    private final ReservationControlRepository reservationControlRepository;
    private final ReservationInventoryAdapterOut reservationInventoryAdapterOut;
    private final SmtpMailMessageAdapterOut smtpMailMessageAdapterOut;
    private final ReservationInventoryMapper reservationInventoryMapper;
    private final FindStudentAdapterOut findStudentAdapterOut;

    @Scheduled(cron = "0 0 0 * * *")
    public void processPendingReservations() {
        var pendingReservations = reservationControlRepository.findByStatus(PENDING);
        log.info("Found {} pending reservations", pendingReservations.size());

        var toUpdate = pendingReservations.parallelStream()
                .map(reservation -> {
                    try {
                        if (reservationInventoryAdapterOut.checkIfBookIsReservedByIsbn(reservation.getIsbn())) {
                            return updateReservationStatus(reservation);
                        } else {
                            return processReservation(reservation);
                        }
                    } catch (Exception ex) {
                        log.error("Error processing reservation id={}: {}", reservation.getId(), ex.getMessage(), ex);
                        reservation.setStatus(ERROR);
                        reservation.setUpdatedAt(LocalDateTime.now());
                        reservation.setErrorDescription(String.format("Error processing reservation: %s", ex.getMessage()));
                        return reservation;
                    }
                })
                .filter(Objects::nonNull)
                .toList();

        if (!toUpdate.isEmpty()) {
            reservationControlRepository.saveAll(toUpdate);
        }
    }

    private ReservationControlEntity processReservation(ReservationControlEntity reservationControlEntity) {
        return Optional.ofNullable(reservationControlEntity)
                .map(controlEntity -> {
                    try {
                        var reservation = reservationInventoryMapper.toDomain(controlEntity.getIsbn(), controlEntity.getDocument());
                        reservationInventoryAdapterOut.execute(reservation);
                        log.info("Reservation processed: id={}, isbn={}, document={}", controlEntity.getId(), controlEntity.getIsbn(), controlEntity.getDocument());
                        this.notifyReservationByEmail(reservation);
                        return null;
                    } catch (Exception ex) {
                        log.error("Error processing reservation: id={}, isbn={}, document={}, error={}", controlEntity.getId(), controlEntity.getIsbn(), controlEntity.getDocument(), ex.getMessage(), ex);
                        controlEntity.setStatus(ERROR);
                        controlEntity.setUpdatedAt(LocalDateTime.now());
                        controlEntity.setErrorDescription(String.format("Error processing reservation: %s", ex.getMessage()));
                        return controlEntity;
                    }
                })
                .orElse(null);
    }

    private ReservationControlEntity updateReservationStatus(ReservationControlEntity reservationControlEntity) {
        return Optional.ofNullable(reservationControlEntity)
                .map(entity -> {
                    entity.setStatus(CONFIRMED);
                    entity.setUpdatedAt(LocalDateTime.now());
                    log.info("Reservation confirmed: id={}, isbn={}, document={}", entity.getId(), entity.getIsbn(), entity.getDocument());
                    return entity;
                })
                .orElse(null);
    }

    private void notifyReservationByEmail(Reservation reservation) {
        findStudentAdapterOut.findByDocument(reservation.getDocument())
                .ifPresent(student -> {
                    smtpMailMessageAdapterOut.sendMail(MailMessage.builder()
                            .to(student.getEmail())
                            .subject("Reservation Confirmed Successfully")
                            .text(String.format("Your reservation for the book: %s has been confirmed.", reservation.getIsbn()))
                            .build());
                });
    }

}