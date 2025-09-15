package com.example.adapter.out;

import com.example.application.core.domain.User;
import com.example.application.core.port.out.FindUserPortOut;
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