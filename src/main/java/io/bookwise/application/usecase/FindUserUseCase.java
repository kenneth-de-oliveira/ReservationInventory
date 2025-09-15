package io.bookwise.application.usecase;

import io.bookwise.application.core.domain.User;
import io.bookwise.application.core.ports.in.FindUserPortIn;
import io.bookwise.application.core.ports.out.FindUserPortOut;

public class FindUserUseCase implements FindUserPortIn {

    private final FindUserPortOut findUserPortOut;

    public FindUserUseCase(FindUserPortOut findUserPortOut) {
        this.findUserPortOut = findUserPortOut;
    }

    @Override
    public User findByDocument(String document) {
        return findUserPortOut.findByDocument(document).orElseThrow(() -> new RuntimeException("User not found"));
    }

}