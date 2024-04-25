package io.bookwise.application.core.ports.in;

import io.bookwise.application.core.domain.Book;

public interface FindBookPortIn {
    Book findIsbn(String isbn);
}