package io.bookwise.application.usecase;

import io.bookwise.adapters.out.repository.dto.ReservationProjection;
import io.bookwise.adapters.out.repository.dto.ReservationQueue;
import io.bookwise.application.core.domain.Book;
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

    @Mock
    private UpdateStatusReservedBookPortOut updateStatusReservedBookPortOut;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        reservationInventoryUseCase = new ReservationInventoryUseCase(findBookPortOut, findStudentPortOut, publishReservationMessageToQueuePortOut, reservationInventoryPortOut, updateStatusReservedBookPortOut);
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
        Book book = new Book();
        book.setReserved(true);

        when(findBookPortOut.findIsbn(anyString())).thenReturn(Optional.of(book));
        when(findStudentPortOut.findByDocument(anyString())).thenReturn(Optional.of(new Student()));

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
    void findShouldReturnListOfReservations() {
        String document = "123";
        ReservationProjection reservation1 = new ReservationProjection() {
            @Override
            public String getTitle() {
                return "Title 1";
            }

            @Override
            public String getAuthorName() {
                return "Author 1";
            }

            @Override
            public String getIsbn() {
                return "ISBN 1";
            }
        };
        ReservationProjection reservation2 = new ReservationProjection() {
            @Override
            public String getTitle() {
                return "Title 2";
            }

            @Override
            public String getAuthorName() {
                return "Author 2";
            }

            @Override
            public String getIsbn() {
                return "ISBN 2";
            }
        };
        List<ReservationProjection> expectedReservations = Arrays.asList(reservation1, reservation2);

        when(reservationInventoryPortOut.find(document)).thenReturn(expectedReservations);

        List<ReservationProjection> actualReservations = reservationInventoryUseCase.find(document);

        assertEquals(expectedReservations, actualReservations);
    }

    @Test
    void findShouldReturnListOfReservationsEmpty() {
        String document = "123";

        when(reservationInventoryPortOut.find(document)).thenReturn(Collections.emptyList());

        List<ReservationProjection> actualReservations = reservationInventoryUseCase.find(document);

        assertEquals(Collections.emptyList(), actualReservations);
    }

}