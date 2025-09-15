package com.example.application.core.port.in;

import com.example.application.core.domain.Book;

import java.util.List;

public interface FindBookPortIn {
    Book findIsbn(String isbn);
    List<Book> findAll();
}