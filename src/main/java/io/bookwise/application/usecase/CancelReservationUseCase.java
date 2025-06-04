package io.bookwise.application.usecase;

import io.bookwise.adapters.out.mail.MailMessage;
import io.bookwise.adapters.out.repository.dto.CancelReservationRequest;
import io.bookwise.adapters.out.repository.enums.ReservationControlStatus;
import io.bookwise.application.core.ports.in.CancelReservationPortIn;
import io.bookwise.application.core.ports.out.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
    public void cancel(CancelReservationRequest reserve) {
        ReservationControlStatus.get(reserve.cancelReservationAction().value())
                .flatMap(status -> findBookPortOut.findIsbn(reserve.isbn()).stream()
                        .filter(book -> reservationInventoryPortOut.checkIfBookIsReservedByIsbnAndDocument(book.getIsbn(), reserve.document()))
                        .findFirst())
                .ifPresentOrElse(book -> {
                    var student = findStudentPortOut.findByDocument(reserve.document()).orElseThrow(() -> new RuntimeException("Student not found"));
                    cancelReservationPortOut.execute(reserve);
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