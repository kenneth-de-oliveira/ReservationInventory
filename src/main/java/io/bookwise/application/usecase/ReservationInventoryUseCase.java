package io.bookwise.application.usecase;

import io.bookwise.adapters.out.repository.dto.ReservationQueue;
import io.bookwise.adapters.out.repository.dto.ReserveInfo;
import io.bookwise.application.core.domain.Reservation;
import io.bookwise.application.core.ports.in.ReservationInventoryPortIn;
import io.bookwise.application.core.ports.out.FindBookPortOut;
import io.bookwise.application.core.ports.out.FindStudentPortOut;
import io.bookwise.application.core.ports.out.PublishReservationMessageToQueuePortOut;
import io.bookwise.application.core.ports.out.ReservationInventoryPortOut;

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
    public void reserve(Reservation reservation) {
        reservationInventoryPortOut.execute(reservation);
    }

    @Override
    public List<ReserveInfo> findAllByDocument(String document) {
        return reservationInventoryPortOut.findAllByDocument(document);
    }

    @Override
    public ReservationQueue sendToReservationQueue(String isbn, String document) {
        return findBookPortOut.findIsbn(isbn).stream()
                .peek(domain -> domain.setReserved(reservationInventoryPortOut.checkIfBookIsReservedByIsbn(isbn)))
                .filter(book -> !book.isReserved())
                .findFirst()
                .map(book -> {
                    var student = findStudentPortOut.findByDocument(document).orElseThrow(() -> new RuntimeException("Student not Found"));
                    return publishReservationMessageToQueuePortOut.send(book.getIsbn(), student.getDocument());
                }).orElseThrow(() -> new RuntimeException("Book is already reserved or not found"));
    }

}