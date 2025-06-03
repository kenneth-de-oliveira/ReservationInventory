package io.bookwise.application.usecase;

import io.bookwise.adapters.out.mail.MailMessage;
import io.bookwise.adapters.out.repository.dto.ReservationQueue;
import io.bookwise.adapters.out.repository.dto.ReserveInfo;
import io.bookwise.application.core.domain.Reservation;
import io.bookwise.application.core.ports.in.ReservationInventoryPortIn;
import io.bookwise.application.core.ports.out.*;

import java.util.List;

public class ReservationInventoryUseCase implements ReservationInventoryPortIn {

    private final FindBookPortOut findBookPortOut;
    private final FindStudentPortOut findStudentPortOut;
    private final ReservationMessageQueuePublisherPortOut reservationMessageQueuePublisherPortOut;
    private final ReservationInventoryPortOut reservationInventoryPortOut;
    private final SmtpMailMessagePortOut smtpMailMessagePortOut;

    public ReservationInventoryUseCase(
            FindBookPortOut findBookPortOut,
            FindStudentPortOut findStudentPortOut,
            ReservationMessageQueuePublisherPortOut reservationMessageQueuePublisherPortOut,
            ReservationInventoryPortOut reservationInventoryPortOut,
            SmtpMailMessagePortOut smtpMailMessagePortOut) {
        this.findBookPortOut = findBookPortOut;
        this.findStudentPortOut = findStudentPortOut;
        this.reservationMessageQueuePublisherPortOut = reservationMessageQueuePublisherPortOut;
        this.reservationInventoryPortOut = reservationInventoryPortOut;
        this.smtpMailMessagePortOut = smtpMailMessagePortOut;
    }

    @Override
    public void reserve(Reservation reservation) {
        findStudentPortOut.findByDocument(reservation.getDocument()).stream()
                .filter(student -> !reservationInventoryPortOut.checkIfBookIsReservedByIsbnAndDocument(reservation.getIsbn(), student.getDocument()))
                .findFirst()
                .map(student -> {
                    reservationInventoryPortOut.execute(reservation);
                    var mail = MailMessage.builder()
                            .to(student.getEmail())
                            .subject("Reservation Confirmed Successfully")
                            .text(String.format("Your reservation for the book: %s has been confirmed.", reservation.getIsbn()))
                            .build();
                    smtpMailMessagePortOut.sendMail(mail);
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
                    var student = findStudentPortOut.findByDocument(document).orElseThrow(() -> new RuntimeException("Student not Found"));
                    return reservationMessageQueuePublisherPortOut.sendToQueueRequest(book.getIsbn(), student.getDocument());
                }).orElseThrow(() -> new RuntimeException("Book is already reserved or not found"));
    }

}