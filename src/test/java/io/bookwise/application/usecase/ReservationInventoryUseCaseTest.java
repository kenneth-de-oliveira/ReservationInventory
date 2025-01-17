package io.bookwise.application.usecase;

import io.bookwise.adapters.out.repository.dto.Reservation;
import io.bookwise.adapters.out.repository.dto.ReservationQueue;
import io.bookwise.application.core.domain.Book;
import io.bookwise.application.core.domain.Student;
import io.bookwise.application.core.ports.out.FindBookPortOut;
import io.bookwise.application.core.ports.out.FindStudentPortOut;
import io.bookwise.application.core.ports.out.PublishReservationMessageToQueuePortOut;
import io.bookwise.application.core.ports.out.ReservationInventoryPortOut;
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
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class ReservationInventoryUseCaseTest {

    private ReservationInventoryUseCase reservationInventoryUseCase;

    @Mock
    private FindBookPortOut findBookPortOut;

    @Mock
    private FindStudentPortOut findStudentPortOut;

    @Mock
    private PublishReservationMessageToQueuePortOut publishReservationMessageToQueuePortOut;

    @Mock
    private ReservationInventoryPortOut reservationInventoryPortOut;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        reservationInventoryUseCase = new ReservationInventoryUseCase(findBookPortOut, findStudentPortOut, publishReservationMessageToQueuePortOut, reservationInventoryPortOut);
    }

    @Test
    void reservationShouldThrowExceptionWhenBookNotFound() {
        when(findBookPortOut.findIsbn(anyString())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> reservationInventoryUseCase.sendToReservationQueue("123", "456"));
    }

    @Test
    void reservationShouldThrowExceptionWhenStudentNotFound() {
        when(findBookPortOut.findIsbn(anyString())).thenReturn(Optional.of(new Book()));
        when(findStudentPortOut.findByDocument(anyString())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> reservationInventoryUseCase.sendToReservationQueue("123", "456"));
    }

    @Test
    void reservationShouldThrowExceptionWhenBookIsAlreadyReserved() {
        when(findBookPortOut.findIsbn(anyString())).thenReturn(Optional.of(new Book()));
        when(findStudentPortOut.findByDocument(anyString())).thenReturn(Optional.of(new Student()));
        when(reservationInventoryPortOut.checkIfBookIsReservedByIsbn(anyString())).thenReturn(true);

        assertThrows(RuntimeException.class, () -> reservationInventoryUseCase.sendToReservationQueue("123", "456"));
    }

    @Test
    void reservationShouldSendMessageToQueueWhenBookIsAvailableAndStudentExists() {
        Book book = new Book();
        book.setReserved(false);

        when(findBookPortOut.findIsbn(anyString())).thenReturn(Optional.of(book));
        when(findStudentPortOut.findByDocument(anyString())).thenReturn(Optional.of(new Student()));
        when(publishReservationMessageToQueuePortOut.send(anyString(), anyString())).thenReturn(new ReservationQueue(UUID.randomUUID()));

        assertDoesNotThrow(() -> reservationInventoryUseCase.sendToReservationQueue("123", "123"));

    }

    @Test
    void findAllByDocumentShouldReturnListOfReservations() {
        String document = "123";

        Reservation reservation1 = new Reservation("title", "author", "isbn");
        Reservation reservation2 = new Reservation("title2", "author2", "isbn2");

        List<Reservation> expectedReservations = Arrays.asList(reservation1, reservation2);

        when(reservationInventoryPortOut.findAllByDocument(document)).thenReturn(expectedReservations);

        List<Reservation> actualReservations = reservationInventoryUseCase.findAllByDocument(document);

        assertEquals(expectedReservations, actualReservations);
    }

    @Test
    void findAllByDocumentShouldReturnListOfReservationsEmpty() {
        String document = "123";

        when(reservationInventoryPortOut.findAllByDocument(document)).thenReturn(Collections.emptyList());

        List<Reservation> actualReservations = reservationInventoryUseCase.findAllByDocument(document);

        assertEquals(Collections.emptyList(), actualReservations);
    }

}