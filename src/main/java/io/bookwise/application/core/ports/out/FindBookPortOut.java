package io.bookwise.application.core.ports.out;

import io.bookwise.application.core.domain.Book;

import java.util.Optional;

public interface FindBookPortOut {
    Optional<Book> findIsbn(String isbn);
}