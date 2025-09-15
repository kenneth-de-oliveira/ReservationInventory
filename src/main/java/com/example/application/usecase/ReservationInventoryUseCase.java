package com.example.application.usecase;

import com.example.application.core.dto.Email;
import com.example.adapter.out.repository.dto.ReservationQueue;
import com.example.adapter.out.repository.dto.ReserveInfo;
import com.example.application.core.domain.Reservation;
import com.example.application.core.port.in.ReservationInventoryPortIn;
import com.example.application.core.port.out.*;

import java.util.List;

public class ReservationInventoryUseCase implements ReservationInventoryPortIn {

    private final FindBookPortOut findBookPortOut;
    private final FindUserPortOut findUserPortOut;
    private final ReservationMessageQueuePublisherPortOut reservationMessageQueuePublisherPortOut;
    private final ReservationInventoryPortOut reservationInventoryPortOut;
    private final EmailServicePortOut emailServicePortOut;

    public ReservationInventoryUseCase(
            FindBookPortOut findBookPortOut,
            FindUserPortOut findUserPortOut,
            ReservationMessageQueuePublisherPortOut reservationMessageQueuePublisherPortOut,
            ReservationInventoryPortOut reservationInventoryPortOut,
            EmailServicePortOut emailServicePortOut) {
        this.findBookPortOut = findBookPortOut;
        this.findUserPortOut = findUserPortOut;
        this.reservationMessageQueuePublisherPortOut = reservationMessageQueuePublisherPortOut;
        this.reservationInventoryPortOut = reservationInventoryPortOut;
        this.emailServicePortOut = emailServicePortOut;
    }

    @Override
    public void reserve(Reservation reservation) {
        findUserPortOut.findByDocument(reservation.getDocument()).stream()
                .filter(student -> !reservationInventoryPortOut.checkIfBookIsReservedByIsbnAndDocument(reservation.getIsbn(), student.getDocument()))
                .findFirst()
                .map(student -> {
                    reservationInventoryPortOut.execute(reservation);
                    var email = Email.builder()
                            .to(student.getEmail())
                            .subject("Reservation Confirmed Successfully")
                            .text(String.format("Your reservation for the book: %s has been confirmed.", reservation.getIsbn()))
                            .build();
                    emailServicePortOut.send(email);
                    return null;
                });
    }

    @Override
    public List<ReserveInfo> findAllByDocument(String document) {
        return reservationInventoryPortOut.findAllByDocument(document);
    }

    @Override
    public ReservationQueue enqueueReservationRequest(String isbn, String document) {
        return findBookPortOut.findIsbn(isbn).stream()
                .peek(domain -> domain.setReserved(reservationInventoryPortOut.checkIfBookIsReservedByIsbn(isbn)))
                .filter(book -> !book.isReserved())
                .findFirst()
                .map(book -> {
                    var student = findUserPortOut.findByDocument(document).orElseThrow(() -> new RuntimeException("Student not Found"));
                    return reservationMessageQueuePublisherPortOut.sendToQueueRequest(book.getIsbn(), student.getDocument());
                }).orElseThrow(() -> new RuntimeException("Book is already reserved or not found"));
    }

}