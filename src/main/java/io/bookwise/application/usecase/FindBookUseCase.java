package io.bookwise.application.usecase;

import io.bookwise.application.core.domain.Book;
import io.bookwise.application.core.ports.in.FindBookPortIn;
import io.bookwise.application.core.ports.out.FindBookPortOut;

import java.util.List;

public class FindBookUseCase implements FindBookPortIn {

    private final FindBookPortOut findBookPortOut;

    public FindBookUseCase(FindBookPortOut findBookPortOut) {
        this.findBookPortOut = findBookPortOut;
    }

    @Override
    public Book findIsbn(String isbn) {
        return findBookPortOut.findIsbn(isbn).orElseThrow(() -> new RuntimeException("Book not Found"));
    }

    @Override
    public List<Book> findAllByCategory(String category) {
        return findBookPortOut.findAllByCategory(category);
    }

}