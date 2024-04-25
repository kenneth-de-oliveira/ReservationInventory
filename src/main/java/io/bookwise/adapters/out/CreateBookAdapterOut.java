package io.bookwise.adapters.out;

import io.bookwise.adapters.out.mapper.BookMapper;
import io.bookwise.adapters.out.repository.BookRepository;
import io.bookwise.adapters.out.repository.entity.BookEntity;
import io.bookwise.application.core.domain.Book;
import io.bookwise.application.core.ports.out.CreateBookPortOut;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreateBookAdapterOut implements CreateBookPortOut {

    private final BookRepository repository;

    @Override
    public Book create(Book book) {
        log.info("Creating book: {}", book);
        BookEntity entity = BookMapper.toEntity(book);
        var bookEntity = repository.save(entity);
        log.info("Book created: {}", bookEntity);
        return BookMapper.toDomain(bookEntity);
    }

}