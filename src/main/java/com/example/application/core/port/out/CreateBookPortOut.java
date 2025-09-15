package com.example.application.core.port.out;

import com.example.application.core.domain.Book;

public interface CreateBookPortOut {
    void create(Book book);
}