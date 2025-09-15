package com.example.application.usecase;

import com.example.application.core.domain.Book;
import com.example.application.core.domain.Reservation;
import com.example.application.core.domain.User;
import com.example.application.core.port.out.CancelReservationPortOut;
import com.example.application.core.port.out.FindBookPortOut;
import com.example.application.core.port.out.ReservationInventoryPortOut;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class CancelReservationUseCaseTest {

    private CancelReservationPortOut cancelReservationPortOut;

    private FindBookPortOut findBookPortOut;

    private ReservationInventoryPortOut reservationInventoryPortOut;

    private CancelReservationUseCase cancelReservationUseCase;

    @BeforeEach
    void setUp() {
        cancelReservationPortOut = mock(CancelReservationPortOut.class);
        findBookPortOut = mock(FindBookPortOut.class);
        reservationInventoryPortOut = mock(ReservationInventoryPortOut.class);
        cancelReservationUseCase = new CancelReservationUseCase(cancelReservationPortOut, findBookPortOut, reservationInventoryPortOut);
    }

    @Test
    void shouldCancelReservationSuccessfully() {

        Reservation reservation = mock(Reservation.class);

        Book book = mock(Book.class);
        when(book.getIsbn()).thenReturn("123");
        when(reservation.getIsbn()).thenReturn("123");
        when(reservation.getDocument()).thenReturn("doc1");

        when(findBookPortOut.findIsbn(Mockito.anyString())).thenReturn(Optional.of(book));
        when(reservationInventoryPortOut.checkIfBookIsReservedByIsbnAndDocument("123", "doc1")).thenReturn(true);

        User user = mock(User.class);
        when(user.getEmail()).thenReturn("user@email.com");

        assertDoesNotThrow(() -> cancelReservationUseCase.cancel(reservation));

        verify(cancelReservationPortOut).execute(reservation);

    }

    @Test
    void shouldThrowExceptionWhenReservationNotFoundOrAlreadyCancelled() {

        Reservation reservation = mock(Reservation.class);

        Book book = mock(Book.class);
        when(book.getIsbn()).thenReturn("123");
        when(reservation.getIsbn()).thenReturn("123");
        when(reservation.getDocument()).thenReturn("doc1");

        when(findBookPortOut.findIsbn(Mockito.anyString())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> cancelReservationUseCase.cancel(reservation));

        verify(findBookPortOut).findIsbn(Mockito.anyString());

    }

}
