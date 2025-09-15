package com.example.application.usecase;

import com.example.application.core.domain.Reservation;
import com.example.application.core.port.in.CancelReservationPortIn;
import com.example.application.core.port.out.CancelReservationPortOut;
import com.example.application.core.port.out.FindBookPortOut;
import com.example.application.core.port.out.ReservationInventoryPortOut;

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