package io.bookwise.application.core.ports.in;

import io.bookwise.application.core.domain.Book;

public interface CreateBookPortIn {
    void create(Book book);
}