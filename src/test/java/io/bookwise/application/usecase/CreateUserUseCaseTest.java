package io.bookwise.application.usecase;

import io.bookwise.application.core.domain.Address;
import io.bookwise.application.core.domain.User;
import io.bookwise.application.core.ports.out.CreateUserPortOut;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class CreateUserUseCaseTest {

    @Mock
    private CreateUserPortOut createUserPortOut;
    private CreateUserUseCase createStudentUseCase;

    @BeforeEach
    void setUp() {
        createStudentUseCase = new CreateUserUseCase(createUserPortOut);
    }

    @Test
    void createStudent_callsCreateOnPortOut() {
        Address address = new Address("street 123", "city 123", "NY", "12345678");
        User user = new User("123456789", "John Doe", "johndoe@gmail.com", address);

        when(createUserPortOut.create(Mockito.any())).thenReturn(user);

        Assertions.assertDoesNotThrow(() -> {
            createStudentUseCase.create(user);
        });

        verify(createUserPortOut).create(user);
    }

}