package io.bookwise.application.core.ports.in;

import io.bookwise.application.core.domain.User;

public interface CreateUserPortIn {
    User create(User user);
}