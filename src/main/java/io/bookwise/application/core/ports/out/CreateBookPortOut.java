package io.bookwise.application.core.ports.out;

import io.bookwise.application.core.domain.Book;

public interface CreateBookPortOut {
    void create(Book book);
}