package io.bookwise.adapters.out.scheduled;

import io.bookwise.adapters.out.FindStudentAdapterOut;
import io.bookwise.adapters.out.ReservationInventoryAdapterOut;
import io.bookwise.adapters.out.SmtpMailMessageAdapterOut;
import io.bookwise.adapters.out.mail.MailMessage;
import io.bookwise.adapters.out.mapper.ReservationInventoryMapper;
import io.bookwise.adapters.out.repository.ReservationControlRepository;
import io.bookwise.adapters.out.repository.entity.ReservationControlEntity;
import io.bookwise.application.core.domain.Reservation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

import static io.bookwise.adapters.out.repository.enums.ReservationControlStatus.ERROR;
import static io.bookwise.adapters.out.repository.enums.ReservationControlStatus.PENDING;

/**
 * Scheduled task to process pending reservations every day at midnight.
 * It retrieves all pending reservations, processes them, and sends confirmation emails.
 *
 * @author kenneth
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class PendingReservationsChecker {

    private final ReservationControlRepository reservationControlRepository;
    private final ReservationInventoryAdapterOut reservationInventoryAdapterOut;
    private final SmtpMailMessageAdapterOut smtpMailMessageAdapterOut;
    private final ReservationInventoryMapper reservationInventoryMapper;
    private final FindStudentAdapterOut findStudentAdapterOut;

    @Scheduled(cron = "0 0 0 * * *")
    public void processPendingReservations() {
        var reservationControlEntities = reservationControlRepository.findByStatus(PENDING);

        log.info("Found {} pending reservations", reservationControlEntities.size());

        var failedReservations = reservationControlEntities.parallelStream()
                .peek(reservationControlEntity -> log.info("Processing pending reservation: id={}, isbn={}, document={}", reservationControlEntity.getId(), reservationControlEntity.getIsbn(), reservationControlEntity.getDocument()))
                .map(this::processReservationControl)
                .filter(Objects::nonNull)
                .toList();

        if (!failedReservations.isEmpty()) {
            reservationControlRepository.saveAll(failedReservations);
        }
    }

    private ReservationControlEntity processReservationControl(ReservationControlEntity reservationControlEntity) {
        return Optional.ofNullable(reservationControlEntity)
                .map(controlEntity -> {
                    try {
                        var reservation = reservationInventoryMapper.toDomain(controlEntity.getIsbn(), controlEntity.getDocument());
                        reservationInventoryAdapterOut.execute(reservation);
                        log.info("Reservation processed: id={}, isbn={}, document={}", controlEntity.getId(), controlEntity.getIsbn(), controlEntity.getDocument());
                        this.notifyReservationByEmail(reservation);
                        return null;
                    } catch (Exception exception) {
                        log.error("Error processing reservation: id={}, isbn={}, document={}, error={}", controlEntity.getId(), controlEntity.getIsbn(), controlEntity.getDocument(), exception.getMessage(), exception);
                        controlEntity.setStatus(ERROR);
                        return controlEntity;
                    }
                })
                .orElse(null);
    }

    private void notifyReservationByEmail(Reservation reservation) {
        findStudentAdapterOut.findByDocument(reservation.getDocument())
                .ifPresent(student -> {
                    var mailMessage = MailMessage.builder()
                            .to(student.getEmail())
                            .subject("Reservation Confirmed Successfully")
                            .text(String.format("Your reservation for the book: %s has been confirmed.", reservation.getIsbn()))
                            .build();
                    smtpMailMessageAdapterOut.sendMail(mailMessage);
                });
    }

}