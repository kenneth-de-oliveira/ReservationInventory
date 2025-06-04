package io.bookwise.adapters.out.scheduled;

import io.bookwise.adapters.out.CancelReservationAdapterOut;
import io.bookwise.adapters.out.FindStudentAdapterOut;
import io.bookwise.adapters.out.SmtpMailMessageAdapterOut;
import io.bookwise.adapters.out.repository.ReservationControlRepository;
import io.bookwise.adapters.out.repository.entity.ReservationControlEntity;
import io.bookwise.application.core.dto.MailMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static io.bookwise.adapters.out.repository.enums.ReservationControlStatus.CANCELLED_REQUEST;
import static io.bookwise.adapters.out.repository.enums.ReservationControlStatus.ERROR;

/**
 * Scheduled task to process cancelled reservations every day at midnight.
 * It retrieves all cancelled reservations, processes them, and sends confirmation emails.
 *
 * @author kenneth
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CancelledReservationChecker {

    private final ReservationControlRepository reservationControlRepository;
    private final CancelReservationAdapterOut cancelReservationAdapterOut;
    private final SmtpMailMessageAdapterOut smtpMailMessageAdapterOut;
    private final FindStudentAdapterOut findStudentAdapterOut;

    @Scheduled(cron = "0 0 0 * * *")
    public void processCancelledReservations() {
        var cancelledReservations = reservationControlRepository.findByStatus(CANCELLED_REQUEST);
        log.info("Found {} cancelled reservations request", cancelledReservations.size());

        var toUpdate = cancelledReservations.parallelStream()
                .map(this::safeProcessCancelReservation)
                .filter(Objects::nonNull)
                .toList();

        updateReservationsIfNeeded(toUpdate);
    }

    private ReservationControlEntity safeProcessCancelReservation(ReservationControlEntity reservation) {
        try {
            return processCancelReservation(reservation);
        } catch (Exception ex) {
            return handleProcessingError(reservation, ex);
        }
    }

    private ReservationControlEntity processCancelReservation(ReservationControlEntity reservationControlEntity) {
        return Optional.ofNullable(reservationControlEntity)
                .map(controlEntity -> {
                    try {
                        cancelReservationAdapterOut.cancelReservation(controlEntity);
                        log.info("Reservation cancelled processed: id={}, isbn={}, document={}", controlEntity.getId(), controlEntity.getIsbn(), controlEntity.getDocument());
                        this.notifyCancelReservationByEmail(controlEntity);
                        return null;
                    } catch (Exception ex) {
                        log.error("Error processing cancelled reservation: id={}, isbn={}, document={}, error={}", controlEntity.getId(), controlEntity.getIsbn(), controlEntity.getDocument(), ex.getMessage(), ex);
                        controlEntity.setStatus(ERROR);
                        controlEntity.setUpdatedAt(LocalDateTime.now());
                        controlEntity.setErrorDescription(String.format("Error processing cancelled reservation: %s", ex.getMessage()));
                        return controlEntity;
                    }
                })
                .orElse(null);
    }

    private void notifyCancelReservationByEmail(ReservationControlEntity reservationControlEntity) {
        findStudentAdapterOut.findByDocument(reservationControlEntity.getDocument())
                .ifPresent(student -> {
                    smtpMailMessageAdapterOut.sendMail(
                            MailMessage.builder()
                                    .to(student.getEmail())
                                    .subject("Reservation Cancelled Successfully")
                                    .text(String.format("Your reservation for the book: %s has been cancelled.", reservationControlEntity.getIsbn()))
                                    .build()
                    );
                });
    }

    private ReservationControlEntity handleProcessingError(ReservationControlEntity reservation, Exception ex) {
        log.error("Error processing reservation id={}: {}", reservation.getId(), ex.getMessage(), ex);
        reservation.setStatus(ERROR);
        reservation.setUpdatedAt(LocalDateTime.now());
        reservation.setErrorDescription(String.format("Error processing cancelled reservation: %s", ex.getMessage()));
        return reservation;
    }

    private void updateReservationsIfNeeded(List<ReservationControlEntity> toUpdate) {
        if (!toUpdate.isEmpty()) {
            reservationControlRepository.saveAll(toUpdate);
        }
    }

}