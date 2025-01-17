package io.bookwise.application.usecase;

import io.bookwise.application.core.domain.Book;
import io.bookwise.application.core.ports.out.FeatureTogglePortOut;
import io.bookwise.application.core.ports.out.FindBookPortOut;
import io.bookwise.application.core.ports.out.ReservationInventoryPortOut;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class FindBookUseCaseTest {

    @Mock
    private FindBookPortOut findBookPortOut;

    @Mock
    private FeatureTogglePortOut featureTogglePortOut;

    @Mock
    private ReservationInventoryPortOut reservationInventoryPortOut;

    @InjectMocks
    private FindBookUseCase findBookUseCase;

    @Test
    void findIsbn_callsFindIsbnOnPortOut() {
        String isbn = "1234567890";
        Book book = new Book();
        when(findBookPortOut.findIsbn(isbn)).thenReturn(Optional.of(book));
        when(featureTogglePortOut.isEnabled(Mockito.anyString())).thenReturn(false);
        when(reservationInventoryPortOut.checkIfBookIsReservedByIsbn(Mockito.anyString())).thenReturn(true);

        findBookUseCase.findIsbn(isbn);

        verify(findBookPortOut).findIsbn(isbn);
    }

    @Test
    void findAllByCategory_callsFindAllByCategoryOnPortOut() {
        String category = "Fiction";
        Book book1 = new Book();
        Book book2 = new Book();
        List<Book> books = Arrays.asList(book1, book2);

        when(findBookPortOut.findAllByCategory(category)).thenReturn(books);

        findBookUseCase.findAllByCategory(category);

        verify(findBookPortOut).findAllByCategory(category);
    }

}