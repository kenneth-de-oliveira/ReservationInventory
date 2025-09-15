package com.example.application.usecase;

import com.example.application.core.dto.Email;
import com.example.adapter.out.repository.dto.ReserveInfo;
import com.example.adapter.out.repository.dto.ReservationQueue;
import com.example.application.core.domain.Book;
import com.example.application.core.domain.Reservation;
import com.example.application.core.domain.User;
import com.example.application.core.port.out.*;
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
    private FindUserPortOut findUserPortOut;

    @Mock
    private ReservationMessageQueuePublisherPortOut reservationMessageQueuePublisherPortOut;

    @Mock
    private ReservationInventoryPortOut reservationInventoryPortOut;

    @Mock
    private EmailServicePortOut emailServicePortOut;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        reservationInventoryUseCase = new ReservationInventoryUseCase(findBookPortOut, findUserPortOut, reservationMessageQueuePublisherPortOut, reservationInventoryPortOut, emailServicePortOut);
    }

    @Test
    void reservationShouldThrowExceptionWhenBookNotFound() {
        when(findBookPortOut.findIsbn(anyString())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> reservationInventoryUseCase.enqueueReservationRequest("123", "456"));
    }

    @Test
    void reservationShouldThrowExceptionWhenStudentNotFound() {
        when(findBookPortOut.findIsbn(anyString())).thenReturn(Optional.of(new Book()));
        when(findUserPortOut.findByDocument(anyString())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> reservationInventoryUseCase.enqueueReservationRequest("123", "456"));
    }

    @Test
    void reservationShouldThrowExceptionWhenBookIsAlreadyReserved() {
        when(findBookPortOut.findIsbn(anyString())).thenReturn(Optional.of(new Book()));
        when(findUserPortOut.findByDocument(anyString())).thenReturn(Optional.of(new User()));
        when(reservationInventoryPortOut.checkIfBookIsReservedByIsbn(anyString())).thenReturn(true);

        assertThrows(RuntimeException.class, () -> reservationInventoryUseCase.enqueueReservationRequest("123", "456"));
    }

    @Test
    void reservationShouldSendMessageToQueueWhenBookIsAvailableAndStudentExists() {
        Book book = new Book();
        book.setIsbn("123");
        book.setReserved(false);

        User user = new User();
        user.setDocument("123");

        when(findBookPortOut.findIsbn(anyString())).thenReturn(Optional.of(book));
        when(reservationInventoryPortOut.checkIfBookIsReservedByIsbn(anyString())).thenReturn(false);
        when(findUserPortOut.findByDocument(anyString())).thenReturn(Optional.of(user));
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

       User user = new User();
       user.setEmail("student@email.com");

       when(findUserPortOut.findByDocument("123")).thenReturn(Optional.of(user));

       assertDoesNotThrow(() -> reservationInventoryUseCase.reserve(reservation));

       verify(reservationInventoryPortOut).execute(reservation);
       verify(emailServicePortOut).send(any(Email.class));
   }

}