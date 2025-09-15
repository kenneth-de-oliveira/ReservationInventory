package com.example.application.usecase;

import com.example.application.core.domain.User;
import com.example.application.core.port.in.FindUserPortIn;
import com.example.application.core.port.out.FindUserPortOut;

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