package io.bookwise.adapters.out;

import io.bookwise.application.core.domain.User;
import io.bookwise.application.core.ports.out.FindUserPortOut;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class FindUserAdapterOut implements FindUserPortOut {

    @Override
    public Optional<User> findByDocument(String document) {
        throw new RuntimeException("Not implemented yet");
    }

}