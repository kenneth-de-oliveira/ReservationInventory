package io.bookwise.application.usecase;

import io.bookwise.application.core.domain.Book;
import io.bookwise.application.core.ports.out.CreateBookPortOut;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
public class CreateBookUseCaseTest {

    private CreateBookPortOut createBookPortOut;
    private CreateBookUseCase createBookUseCase;

    @BeforeEach
    void setUp() {
        createBookPortOut = Mockito.mock(CreateBookPortOut.class);
        createBookUseCase = new CreateBookUseCase(createBookPortOut);
    }

    @Test
    void createBook_callsCreateOnPortOut() {
        Book book = new Book();

        Assertions.assertDoesNotThrow(() -> {
            createBookUseCase.create(book);
        });

        verify(createBookPortOut).create(book);
    }

}