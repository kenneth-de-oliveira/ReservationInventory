package io.bookwise.application.usecase;

import io.bookwise.application.core.domain.Book;
import io.bookwise.application.core.domain.Reservation;
import io.bookwise.application.core.domain.Student;
import io.bookwise.application.core.dto.MailMessage;
import io.bookwise.application.core.ports.out.*;
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

    private FindStudentPortOut findStudentPortOut;

    private ReservationInventoryPortOut reservationInventoryPortOut;

    private SmtpMailMessagePortOut smtpMailMessagePortOut;

    private CancelReservationUseCase cancelReservationUseCase;

    @BeforeEach
    void setUp() {
        cancelReservationPortOut = mock(CancelReservationPortOut.class);
        findBookPortOut = mock(FindBookPortOut.class);
        findStudentPortOut = mock(FindStudentPortOut.class);
        reservationInventoryPortOut = mock(ReservationInventoryPortOut.class);
        smtpMailMessagePortOut = mock(SmtpMailMessagePortOut.class);
        cancelReservationUseCase = new CancelReservationUseCase(cancelReservationPortOut, findBookPortOut, findStudentPortOut, reservationInventoryPortOut, smtpMailMessagePortOut);
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

        Student student = mock(Student.class);
        when(student.getEmail()).thenReturn("student@email.com");

        when(findStudentPortOut.findByDocument(Mockito.anyString())).thenReturn(Optional.of(student));

        assertDoesNotThrow(() -> cancelReservationUseCase.cancel(reservation));

        verify(cancelReservationPortOut).execute(reservation);
        verify(smtpMailMessagePortOut).sendMail(any(MailMessage.class));

    }

    @Test
    void shouldThrowExceptionWhenReservationNotFoundOrAlreadyCancelled() {

        Reservation request = mock(Reservation.class);

        Book book = mock(Book.class);
        when(book.getIsbn()).thenReturn("123");
        when(request.getIsbn()).thenReturn("123");
        when(request.getDocument()).thenReturn("doc1");

        when(findBookPortOut.findIsbn(Mockito.anyString())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> cancelReservationUseCase.cancel(request));

        verify(findBookPortOut).findIsbn(Mockito.anyString());

    }


}
