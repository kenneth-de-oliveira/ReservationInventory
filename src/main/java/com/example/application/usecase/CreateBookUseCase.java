package com.example.application.usecase;

import com.example.application.core.domain.Book;
import com.example.application.core.port.in.CreateBookPortIn;
import com.example.application.core.port.out.CreateBookPortOut;

public class CreateBookUseCase implements CreateBookPortIn {

    private final CreateBookPortOut createBookPortOut;

    public CreateBookUseCase(CreateBookPortOut createBookPortOut) {
        this.createBookPortOut = createBookPortOut;
    }

    @Override
    public void create(Book book) {
        createBookPortOut.create(book);
    }

}