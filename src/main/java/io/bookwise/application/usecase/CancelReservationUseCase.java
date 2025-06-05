package io.bookwise.application.usecase;

import io.bookwise.application.core.domain.Reservation;
import io.bookwise.application.core.dto.MailMessage;
import io.bookwise.application.core.ports.in.CancelReservationPortIn;
import io.bookwise.application.core.ports.out.*;

public class CancelReservationUseCase implements CancelReservationPortIn {

    private final CancelReservationPortOut cancelReservationPortOut;
    private final FindBookPortOut findBookPortOut;
    private final FindStudentPortOut findStudentPortOut;
    private final ReservationInventoryPortOut reservationInventoryPortOut;
    private final SmtpMailMessagePortOut smtpMailMessagePortOut;

    public CancelReservationUseCase(CancelReservationPortOut cancelReservationPortOut,
                                    FindBookPortOut findBookPortOut,
                                    FindStudentPortOut findStudentPortOut,
                                    ReservationInventoryPortOut reservationInventoryPortOut,
                                    SmtpMailMessagePortOut smtpMailMessagePortOut) {
        this.cancelReservationPortOut = cancelReservationPortOut;
        this.findBookPortOut = findBookPortOut;
        this.findStudentPortOut = findStudentPortOut;
        this.reservationInventoryPortOut = reservationInventoryPortOut;
        this.smtpMailMessagePortOut = smtpMailMessagePortOut;
    }

    @Override
    public void cancel(Reservation reservation) {
        findBookPortOut.findIsbn(reservation.getIsbn()).stream()
                .filter(book -> reservationInventoryPortOut.checkIfBookIsReservedByIsbnAndDocument(book.getIsbn(), reservation.getDocument()))
                .findFirst()
                .ifPresentOrElse(book -> {
                    var student = findStudentPortOut.findByDocument(reservation.getDocument()).orElseThrow(() -> new RuntimeException("Student not found"));
                    cancelReservationPortOut.execute(reservation);
                    smtpMailMessagePortOut.sendMail(MailMessage.builder()
                            .to(student.getEmail())
                            .subject("Reservation Cancelled Successfully")
                            .text(String.format("Your reservation for the book: %s has been cancelled.", book.getIsbn()))
                            .build());
                }, () -> {
                    throw new RuntimeException("Reservation not found or reservation already cancelled.");
                });
    }

}