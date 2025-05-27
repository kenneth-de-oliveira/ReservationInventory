package io.bookwise.application.usecase;

import io.bookwise.adapters.out.repository.dto.ReserveInfo;
import io.bookwise.adapters.out.repository.dto.ReservationQueue;
import io.bookwise.application.core.domain.Book;
import io.bookwise.application.core.domain.Reservation;
import io.bookwise.application.core.domain.Student;
import io.bookwise.application.core.ports.in.ReservationInventoryPortIn;
import io.bookwise.application.core.ports.out.*;

import java.util.List;

public class ReservationInventoryUseCase implements ReservationInventoryPortIn {

    private final FindBookPortOut findBookPortOut;
    private final FindStudentPortOut findStudentPortOut;
    private final PublishReservationMessageToQueuePortOut publishReservationMessageToQueuePortOut;
    private final ReservationInventoryPortOut reservationInventoryPortOut;

    public ReservationInventoryUseCase(
            FindBookPortOut findBookPortOut,
            FindStudentPortOut findStudentPortOut,
            PublishReservationMessageToQueuePortOut publishReservationMessageToQueuePortOut,
            ReservationInventoryPortOut reservationInventoryPortOut) {
        this.findBookPortOut = findBookPortOut;
        this.findStudentPortOut = findStudentPortOut;
        this.publishReservationMessageToQueuePortOut = publishReservationMessageToQueuePortOut;
        this.reservationInventoryPortOut = reservationInventoryPortOut;
    }

    @Override
    public void init(Reservation reservation) {
        reservationInventoryPortOut.init(reservation);
    }

    @Override
    public List<ReserveInfo> findAllByDocument(String document) {
        return reservationInventoryPortOut.findAllByDocument(document);
    }

    @Override
    public ReservationQueue sendToReservationQueue(String isbn, String document) {
        var bookDomain = findBookPortOut.findIsbn(isbn).map(book -> {
            book.setReserved(reservationInventoryPortOut.checkIfBookIsReservedByIsbn(isbn));
            return book;
        }).orElseThrow(() -> new RuntimeException("Book not found"));
        var student = findStudentPortOut.findByDocument(document).orElseThrow(() -> new RuntimeException("Student not Found"));
        this.checkIfBookIsReservedByIsbn(isbn);
        return sendToReservationQueue(bookDomain, student);
    }

    private void checkIfBookIsReservedByIsbn(String isbn) {
        if (reservationInventoryPortOut.checkIfBookIsReservedByIsbn(isbn)) {
            throw new RuntimeException("Book is already reserved");
        }
    }

    private ReservationQueue sendToReservationQueue(Book book, Student student) {
        if (book.isReserved()) {
            throw new RuntimeException("Book is already reserved");
        } else {
            return publishReservationMessageToQueuePortOut.send(book.getIsbn(), student.getDocument());
        }
    }

}