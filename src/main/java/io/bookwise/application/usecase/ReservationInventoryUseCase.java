package io.bookwise.application.usecase;

import io.bookwise.adapters.out.repository.dto.ReservationQueue;
import io.bookwise.application.core.domain.Book;
import io.bookwise.application.core.domain.Reservation;
import io.bookwise.application.core.domain.Student;
import io.bookwise.application.core.ports.in.ReservationInventoryPortIn;
import io.bookwise.application.core.ports.out.*;

public class ReservationInventoryUseCase implements ReservationInventoryPortIn {

    private final FindBookPortOut findBookPortOut;
    private final FindStudentPortOut findStudentPortOut;
    private final PublishReservationMessageToQueuePortOut publishReservationMessageToQueuePortOut;
    private final ReservationInventoryPortOut reservationInventoryPortOut;
    private final UpdateStatusReservedBookPortOut updateStatusReservedBookPortOut;

    public ReservationInventoryUseCase(
            FindBookPortOut findBookPortOut,
            FindStudentPortOut findStudentPortOut,
            PublishReservationMessageToQueuePortOut publishReservationMessageToQueuePortOut,
            ReservationInventoryPortOut reservationInventoryPortOut,
            UpdateStatusReservedBookPortOut updateStatusReservedBookPortOut) {
        this.findBookPortOut = findBookPortOut;
        this.findStudentPortOut = findStudentPortOut;
        this.publishReservationMessageToQueuePortOut = publishReservationMessageToQueuePortOut;
        this.reservationInventoryPortOut = reservationInventoryPortOut;
        this.updateStatusReservedBookPortOut = updateStatusReservedBookPortOut;
    }

    @Override
    public void init(Reservation reservation) {
        reservationInventoryPortOut.init(reservation);
    }

    @Override
    public ReservationQueue sendToReservationQueue(String isbn, String document) {
        var book = findBookPortOut.findIsbn(isbn).orElseThrow(() -> new RuntimeException("Book not Found"));
        var student = findStudentPortOut.findByDocument(document).orElseThrow(() -> new RuntimeException("Student not Found"));
        var reservationQueue = sendToReservationQueue(book, student);
        updateStatusReservedBookPortOut.update(isbn);
        return reservationQueue;
    }

    private ReservationQueue sendToReservationQueue(Book book, Student student) {
        if (book.isReserved()) {
            throw new RuntimeException("Book is already reserved");
        } else {
            return publishReservationMessageToQueuePortOut.send(book.getIsbn(), student.getDocument());
        }
    }

}