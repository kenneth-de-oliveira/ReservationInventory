package com.example.application.core.port.out;

import com.example.application.core.domain.User;

import java.util.Optional;

public interface FindUserPortOut {
    Optional<User> findByDocument(String document);
}