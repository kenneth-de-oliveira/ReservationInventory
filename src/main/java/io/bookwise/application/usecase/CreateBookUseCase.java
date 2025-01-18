package io.bookwise.application.usecase;

import io.bookwise.application.core.domain.Book;
import io.bookwise.application.core.ports.in.CreateBookPortIn;
import io.bookwise.application.core.ports.out.CreateBookPortOut;

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