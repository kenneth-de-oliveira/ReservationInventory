package io.bookwise.adapters.out;

import io.bookwise.adapters.out.repository.ReservationControlRepository;
import io.bookwise.adapters.out.repository.dto.CancelReservationRequest;
import io.bookwise.adapters.out.repository.entity.ReservationControlEntity;
import io.bookwise.application.core.ports.out.CancelReservationPortOut;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.stream.Stream;

import static io.bookwise.adapters.out.repository.enums.ReservationControlStatus.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class CancelReservationAdapterOut implements CancelReservationPortOut {

    private final ReservationInventoryAdapterOut reservationInventoryAdapterOut;
    private final ReservationControlRepository reservationControlRepository;

    @Override
    public void execute(CancelReservationRequest reserve) {
        log.info("Cancel reservation of book for ISBN: {} for student: {}", reserve.isbn(), reserve.document());
        this.cancel(reserve);
        log.info("The cancellation of the Reservation was successfully requested for ISBN: {} and student: {}", reserve.isbn(), reserve.document());
    }

    private void cancel(CancelReservationRequest reserve) {
        Stream.ofNullable(reserve)
                .filter(cancelReservationRequest -> this.reservationControlRepository.findByIsbnAndStatus(cancelReservationRequest.isbn(), CANCELLED_REQUEST).isEmpty())
                .peek(this::saveCancelledReservationRequest)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Reservation already cancelled for ISBN: " + reserve.isbn() + " and student: " + reserve.document()));
    }

    private void saveCancelledReservationRequest(CancelReservationRequest reserve) {
        Stream.ofNullable(reserve)
                .filter(isbnValue -> Objects.nonNull(isbnValue.isbn()))
                .peek(reservationValue -> reservationControlRepository.findByIsbnAndStatus(reservationValue.isbn(), CONFIRMED)
                        .ifPresent(existingControl -> {
                            existingControl.setStatus(CANCELLED_REQUEST);
                            existingControl.setUpdatedAt(LocalDateTime.now());
                            reservationControlRepository.save(existingControl);
                        }))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Failed to request cancellation of book reservation for ISBN: " + reserve.isbn() + " and student: " + reserve.document()));
    }

    public void cancelReservation(ReservationControlEntity reservationControlEntity) {
        Stream.ofNullable(reservationControlEntity)
                .filter(reservationValue -> Objects.nonNull(reservationValue.getIsbn()))
                .filter(reservationValue -> reservationInventoryAdapterOut.checkIfBookIsReservedByIsbnAndDocument(reservationValue.getIsbn(), reservationValue.getDocument()))
                .peek(reservationValue -> reservationControlRepository.findByIsbnAndStatus(reservationValue.getIsbn(), CANCELLED_REQUEST)
                        .ifPresent(existingControl -> {
                            existingControl.setStatus(CANCELLED);
                            existingControl.setUpdatedAt(LocalDateTime.now());
                            reservationControlRepository.save(existingControl);
                        }))
                .peek(reservationValue -> reservationInventoryAdapterOut.deleteByIsbnAndDocument(reservationValue.getIsbn(), reservationValue.getDocument()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Error processing cancel reservation for ISBN: " + reservationControlEntity.getIsbn() + " and student: " + reservationControlEntity.getDocument()));
    }

}