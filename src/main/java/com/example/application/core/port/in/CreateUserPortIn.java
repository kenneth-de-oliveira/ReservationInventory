package com.example.application.core.port.in;

import com.example.application.core.domain.User;

public interface CreateUserPortIn {
    User create(User user);
}