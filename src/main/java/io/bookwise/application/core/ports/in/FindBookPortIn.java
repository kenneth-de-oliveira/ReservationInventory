package io.bookwise.application.core.ports.in;

import io.bookwise.application.core.domain.Book;

import java.util.List;

public interface FindBookPortIn {
    Book findIsbn(String isbn);
    List<Book> findAllByCategory(String category);
}