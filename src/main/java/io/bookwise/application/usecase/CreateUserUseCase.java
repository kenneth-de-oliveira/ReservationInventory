package io.bookwise.application.usecase;

import io.bookwise.application.core.domain.User;
import io.bookwise.application.core.ports.in.CreateUserPortIn;
import io.bookwise.application.core.ports.out.CreateUserPortOut;

public class CreateUserUseCase implements CreateUserPortIn {

    private final CreateUserPortOut createUserPortOut;

    public CreateUserUseCase(CreateUserPortOut createUserPortOut) {
        this.createUserPortOut = createUserPortOut;
    }

    @Override
    public User create(User user) {
        return createUserPortOut.create(user);
    }

}