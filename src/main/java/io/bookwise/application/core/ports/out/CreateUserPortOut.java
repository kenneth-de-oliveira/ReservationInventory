package io.bookwise.application.core.ports.out;

import io.bookwise.application.core.domain.User;

public interface CreateUserPortOut {
    User create(User user);
}