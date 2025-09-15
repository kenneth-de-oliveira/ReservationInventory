package com.example.application.core.port.in;

import com.example.application.core.domain.Book;

public interface CreateBookPortIn {
    void create(Book book);
}