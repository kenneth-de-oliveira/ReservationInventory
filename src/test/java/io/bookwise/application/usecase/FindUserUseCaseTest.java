package io.bookwise.application.usecase;

import io.bookwise.application.core.domain.User;
import io.bookwise.application.core.ports.out.FindUserPortOut;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class FindUserUseCaseTest {

    private FindUserPortOut findUserPortOut;
    private FindUserUseCase findStudentByDocumentUseCase;

    @BeforeEach
    void setUp() {
        findUserPortOut = Mockito.mock(FindUserPortOut.class);
        findStudentByDocumentUseCase = new FindUserUseCase(findUserPortOut);
    }

    @Test
    void findByDocument_whenStudentExists_returnsStudent() {
        User expectedUser = new User();
        when(findUserPortOut.findByDocument(anyString())).thenReturn(Optional.of(expectedUser));
        User actualUser = findStudentByDocumentUseCase.findByDocument("123456789");
        assertEquals(expectedUser, actualUser);
    }

    @Test
    void findByDocument_whenStudentDoesNotExist_throwsException() {
        when(findUserPortOut.findByDocument(anyString())).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> findStudentByDocumentUseCase.findByDocument("123456789"));
    }

}