package io.bookwise.application.usecase;

import io.bookwise.application.core.domain.Student;
import io.bookwise.application.core.ports.out.FindStudentPortOut;
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
class FindStudentUseCaseTest {

    private FindStudentPortOut findStudentPortOut;
    private FindStudentUseCase findStudentByDocumentUseCase;

    @BeforeEach
    void setUp() {
        findStudentPortOut = Mockito.mock(FindStudentPortOut.class);
        findStudentByDocumentUseCase = new FindStudentUseCase(findStudentPortOut);
    }

    @Test
    void findByDocument_whenStudentExists_returnsStudent() {
        Student expectedStudent = new Student();
        when(findStudentPortOut.findByDocument(anyString())).thenReturn(Optional.of(expectedStudent));
        Student actualStudent = findStudentByDocumentUseCase.findByDocument("123456789");
        assertEquals(expectedStudent, actualStudent);
    }

    @Test
    void findByDocument_whenStudentDoesNotExist_throwsException() {
        when(findStudentPortOut.findByDocument(anyString())).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> findStudentByDocumentUseCase.findByDocument("123456789"));
    }

}