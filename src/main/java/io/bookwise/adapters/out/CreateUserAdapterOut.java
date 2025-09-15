package io.bookwise.adapters.out;

import io.bookwise.application.core.domain.User;
import io.bookwise.application.core.ports.out.CreateUserPortOut;
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