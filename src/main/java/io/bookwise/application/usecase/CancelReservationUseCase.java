package io.bookwise.application.usecase;

import io.bookwise.application.core.domain.Reservation;
import io.bookwise.application.core.ports.in.CancelReservationPortIn;
import io.bookwise.application.core.ports.out.CancelReservationPortOut;
import io.bookwise.application.core.ports.out.FindBookPortOut;
import io.bookwise.application.core.ports.out.ReservationInventoryPortOut;

public class CancelReservationUseCase implements CancelReservationPortIn {

    private final CancelReservationPortOut cancelReservationPortOut;
    private final FindBookPortOut findBookPortOut;
    private final ReservationInventoryPortOut reservationInventoryPortOut;

    public CancelReservationUseCase(CancelReservationPortOut cancelReservationPortOut,
                                    FindBookPortOut findBookPortOut,
                                    ReservationInventoryPortOut reservationInventoryPortOut) {
        this.cancelReservationPortOut = cancelReservationPortOut;
        this.findBookPortOut = findBookPortOut;
        this.reservationInventoryPortOut = reservationInventoryPortOut;
    }

    @Override
    public void cancel(Reservation reservation) {
        findBookPortOut.findIsbn(reservation.getIsbn()).stream()
                .filter(book -> reservationInventoryPortOut.checkIfBookIsReservedByIsbnAndDocument(book.getIsbn(), reservation.getDocument()))
                .findFirst()
                .ifPresentOrElse(book -> cancelReservationPortOut.execute(reservation),
                        () -> {
                            throw new RuntimeException("Reservation not found or reservation already cancelled.");
                        });
    }

}