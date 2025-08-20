package io.bookwise.application.usecase;

import io.bookwise.application.core.dto.Email;
import io.bookwise.application.core.domain.Address;
import io.bookwise.application.core.domain.Student;
import io.bookwise.application.core.ports.out.CreateStudentPortOut;
import io.bookwise.application.core.ports.out.FindAddressByPostalCodePortOut;
import io.bookwise.application.core.ports.out.EmailServicePortOut;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class CreateStudentUseCaseTest {

    private CreateStudentPortOut createStudentPortOut;
    private CreateStudentUseCase createStudentUseCase;
    private FindAddressByPostalCodePortOut findAddressByPostalCodePortOut;
    private EmailServicePortOut emailServicePortOut;

    @BeforeEach
    void setUp() {
        createStudentPortOut = Mockito.mock(CreateStudentPortOut.class);
        findAddressByPostalCodePortOut = Mockito.mock(FindAddressByPostalCodePortOut.class);
        emailServicePortOut = Mockito.mock(EmailServicePortOut.class);
        createStudentUseCase = new CreateStudentUseCase(createStudentPortOut, findAddressByPostalCodePortOut, emailServicePortOut);
    }

    @Test
    void createStudent_callsCreateOnPortOut() {
        Address address = new Address("street 123", "city 123", "NY", "12345678");
        Student student = new Student("123456789", "John Doe", "johndoe@gmail.com", address);

        when(createStudentPortOut.create(Mockito.any())).thenReturn(student);
        doNothing().when(emailServicePortOut).sendEmail(Mockito.any(Email.class));
        when(findAddressByPostalCodePortOut.find("12345678")).thenReturn(address);

        Assertions.assertDoesNotThrow(() -> {
            createStudentUseCase.create(student);
        });

        verify(createStudentPortOut).create(student);
        verify(findAddressByPostalCodePortOut).find("12345678");
        verify(emailServicePortOut).sendEmail(Mockito.any(Email.class));
    }

}