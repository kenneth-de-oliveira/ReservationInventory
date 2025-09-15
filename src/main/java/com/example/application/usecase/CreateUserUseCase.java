package com.example.application.usecase;

import com.example.application.core.domain.User;
import com.example.application.core.port.in.CreateUserPortIn;
import com.example.application.core.port.out.CreateUserPortOut;

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