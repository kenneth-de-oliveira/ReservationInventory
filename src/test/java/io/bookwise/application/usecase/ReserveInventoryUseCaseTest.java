package io.bookwise.application.usecase;

import io.bookwise.application.core.dto.MailMessage;
import io.bookwise.adapters.out.repository.dto.ReserveInfo;
import io.bookwise.adapters.out.repository.dto.ReservationQueue;
import io.bookwise.application.core.domain.Book;
import io.bookwise.application.core.domain.Reservation;
import io.bookwise.application.core.domain.Student;
import io.bookwise.application.core.ports.out.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class ReserveInventoryUseCaseTest {

    private ReservationInventoryUseCase reservationInventoryUseCase;

    @Mock
    private FindBookPortOut findBookPortOut;

    @Mock
    private FindStudentPortOut findStudentPortOut;

    @Mock
    private ReservationMessageQueuePublisherPortOut reservationMessageQueuePublisherPortOut;

    @Mock
    private ReservationInventoryPortOut reservationInventoryPortOut;

    @Mock
    private SmtpMailMessagePortOut smtpMailMessagePortOut;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        reservationInventoryUseCase = new ReservationInventoryUseCase(findBookPortOut, findStudentPortOut, reservationMessageQueuePublisherPortOut, reservationInventoryPortOut, smtpMailMessagePortOut);
    }

    @Test
    void reservationShouldThrowExceptionWhenBookNotFound() {
        when(findBookPortOut.findIsbn(anyString())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> reservationInventoryUseCase.enqueueReservationRequest("123", "456"));
    }

    @Test
    void reservationShouldThrowExceptionWhenStudentNotFound() {
        when(findBookPortOut.findIsbn(anyString())).thenReturn(Optional.of(new Book()));
        when(findStudentPortOut.findByDocument(anyString())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> reservationInventoryUseCase.enqueueReservationRequest("123", "456"));
    }

    @Test
    void reservationShouldThrowExceptionWhenBookIsAlreadyReserved() {
        when(findBookPortOut.findIsbn(anyString())).thenReturn(Optional.of(new Book()));
        when(findStudentPortOut.findByDocument(anyString())).thenReturn(Optional.of(new Student()));
        when(reservationInventoryPortOut.checkIfBookIsReservedByIsbn(anyString())).thenReturn(true);

        assertThrows(RuntimeException.class, () -> reservationInventoryUseCase.enqueueReservationRequest("123", "456"));
    }

    @Test
    void reservationShouldSendMessageToQueueWhenBookIsAvailableAndStudentExists() {
        Book book = new Book();
        book.setIsbn("123");
        book.setReserved(false);

        Student student = new Student();
        student.setDocument("123");

        when(findBookPortOut.findIsbn(anyString())).thenReturn(Optional.of(book));
        when(reservationInventoryPortOut.checkIfBookIsReservedByIsbn(anyString())).thenReturn(false);
        when(findStudentPortOut.findByDocument(anyString())).thenReturn(Optional.of(student));
        when(reservationMessageQueuePublisherPortOut.sendToQueueRequest(anyString(), anyString()))
                .thenReturn(new ReservationQueue(UUID.randomUUID()));

        assertDoesNotThrow(() -> reservationInventoryUseCase.enqueueReservationRequest("123", "123"));
    }

    @Test
    void findAllByDocumentShouldReturnListOfReservations() {
        String document = "123";

        ReserveInfo reserveInfo1 = new ReserveInfo("title", "author", "isbn");
        ReserveInfo reserveInfo2 = new ReserveInfo("title2", "author2", "isbn2");

        List<ReserveInfo> expectedReserveInfos = Arrays.asList(reserveInfo1, reserveInfo2);

        when(reservationInventoryPortOut.findAllByDocument(document)).thenReturn(expectedReserveInfos);

        List<ReserveInfo> actualReserveInfos = reservationInventoryUseCase.findAllByDocument(document);

        assertEquals(expectedReserveInfos, actualReserveInfos);
    }

    @Test
    void findAllByDocumentShouldReturnListOfReservationsEmpty() {
        String document = "123";

        when(reservationInventoryPortOut.findAllByDocument(document)).thenReturn(Collections.emptyList());

        List<ReserveInfo> actualReserveInfos = reservationInventoryUseCase.findAllByDocument(document);

        assertEquals(Collections.emptyList(), actualReserveInfos);
    }

   @Test
   void reserveShouldCallExecuteOnReservationInventoryPortOut() {
       Reservation reservation = new Reservation();
       reservation.setDocument("123");
       reservation.setIsbn("isbn-1");

       Student student = new Student();
       student.setEmail("student@email.com");

       when(findStudentPortOut.findByDocument("123")).thenReturn(Optional.of(student));

       assertDoesNotThrow(() -> reservationInventoryUseCase.reserve(reservation));

       verify(reservationInventoryPortOut).execute(reservation);
       verify(smtpMailMessagePortOut).sendMail(any(MailMessage.class));
   }

}