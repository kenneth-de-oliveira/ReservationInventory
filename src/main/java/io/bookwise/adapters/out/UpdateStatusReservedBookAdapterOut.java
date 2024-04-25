package io.bookwise.adapters.out;

import io.bookwise.adapters.out.mapper.BookMapper;
import io.bookwise.adapters.out.repository.BookRepository;
import io.bookwise.application.core.ports.out.UpdateStatusReservedBookPortOut;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UpdateStatusReservedBookAdapterOut implements UpdateStatusReservedBookPortOut {

    private final BookRepository repository;

    @Override
    public void update(String isbn) {
        log.info("Updating book by isbn: {}", isbn);
        repository.findByIsbn(isbn).ifPresent(bookEntity -> {
            bookEntity.setReserved(Boolean.TRUE);
            repository.save(bookEntity);
        });
        log.info("Book updated by isbn: {}", isbn);
    }

}