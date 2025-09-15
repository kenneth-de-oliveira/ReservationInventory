package io.bookwise.application.core.ports.out;

import io.bookwise.application.core.domain.User;

import java.util.Optional;

public interface FindUserPortOut {
    Optional<User> findByDocument(String document);
}