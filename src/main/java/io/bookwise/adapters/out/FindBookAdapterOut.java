package io.bookwise.adapters.out;

import io.bookwise.adapters.out.repository.BookRepository;
import io.bookwise.adapters.out.mapper.BookMapper;
import io.bookwise.application.core.domain.Book;
import io.bookwise.application.core.ports.out.FindBookPortOut;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class FindBookAdapterOut implements FindBookPortOut {

    private final BookRepository repository;

    @Override
    public Optional<Book> findIsbn(String isbn) {
        log.info("Finding book by isbn: {}", isbn);
        Optional<Book> book = repository.findByIsbn(isbn).map(BookMapper::toDomain);
        log.info("Book found: {}", book);
        return book;
    }

}