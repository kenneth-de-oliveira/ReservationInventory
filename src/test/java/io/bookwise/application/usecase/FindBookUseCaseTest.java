package io.bookwise.application.usecase;

import io.bookwise.application.core.domain.Book;
import io.bookwise.application.core.ports.out.FindBookPortOut;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class FindBookUseCaseTest {

    @Mock
    private FindBookPortOut findBookPortOut;

    @InjectMocks
    private FindBookUseCase findBookUseCase;

    @Test
    void findIsbn_callsFindIsbnOnPortOut() {
        String isbn = "1234567890";
        Book book = new Book();
        when(findBookPortOut.findIsbn(isbn)).thenReturn(Optional.of(book));

        findBookUseCase.findIsbn(isbn);

        verify(findBookPortOut).findIsbn(isbn);
    }

}