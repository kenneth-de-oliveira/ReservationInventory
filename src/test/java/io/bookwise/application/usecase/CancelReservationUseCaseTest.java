package io.bookwise.application.usecase;

import io.bookwise.adapters.out.repository.enums.ReservationControlStatus;
import io.bookwise.application.core.dto.CancelReservationAction;
import io.bookwise.application.core.dto.CancelReservation;
import io.bookwise.application.core.domain.Book;
import io.bookwise.application.core.domain.Student;
import io.bookwise.application.core.dto.MailMessage;
import io.bookwise.application.core.enums.ReservationStatus;
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

        CancelReservation request = mock(CancelReservation.class);

        Book book = mock(Book.class);
        when(book.getIsbn()).thenReturn("123");
        when(request.isbn()).thenReturn("123");
        when(request.document()).thenReturn("doc1");

        CancelReservationAction cancelReservationAction = mock(CancelReservationAction.class);
        when(request.action()).thenReturn(cancelReservationAction);
        when(cancelReservationAction.value()).thenReturn(ReservationStatus.CANCELLED.getReason());

        when(findBookPortOut.findIsbn(Mockito.anyString())).thenReturn(Optional.of(book));
        when(reservationInventoryPortOut.checkIfBookIsReservedByIsbnAndDocument("123", "doc1")).thenReturn(true);

        Student student = mock(Student.class);
        when(student.getEmail()).thenReturn("student@email.com");

        when(findStudentPortOut.findByDocument(Mockito.anyString())).thenReturn(Optional.of(student));

        assertDoesNotThrow(() -> cancelReservationUseCase.cancel(request));

        verify(cancelReservationPortOut).execute(request);
        verify(smtpMailMessagePortOut).sendMail(any(MailMessage.class));

    }

    @Test
    void shouldThrowExceptionWhenReservationNotFoundOrAlreadyCancelled() {

        CancelReservation request = mock(CancelReservation.class);

        Book book = mock(Book.class);
        when(book.getIsbn()).thenReturn("123");
        when(request.isbn()).thenReturn("123");
        when(request.document()).thenReturn("doc1");

        CancelReservationAction cancelReservationAction = mock(CancelReservationAction.class);
        when(request.action()).thenReturn(cancelReservationAction);
        when(cancelReservationAction.value()).thenReturn(ReservationControlStatus.CANCELLED.getReason());

        when(findBookPortOut.findIsbn(Mockito.anyString())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> cancelReservationUseCase.cancel(request));

        verify(findBookPortOut).findIsbn(Mockito.anyString());

    }


}
