package com.example.application.core.port.out;

import com.example.application.core.domain.Book;

import java.util.List;
import java.util.Optional;

public interface FindBookPortOut {
    Optional<Book> findIsbn(String isbn);
    List<Book> findAll();
}