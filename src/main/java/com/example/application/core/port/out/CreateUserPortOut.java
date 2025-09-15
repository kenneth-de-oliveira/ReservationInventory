package com.example.application.core.port.out;

import com.example.application.core.domain.User;

public interface CreateUserPortOut {
    User create(User user);
}