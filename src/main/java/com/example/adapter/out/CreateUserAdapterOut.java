package com.example.adapter.out;

import com.example.application.core.domain.User;
import com.example.application.core.port.out.CreateUserPortOut;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreateUserAdapterOut implements CreateUserPortOut {

    @Override
    public User create(User user) {
        throw new RuntimeException("Not implemented yet");
    }

}