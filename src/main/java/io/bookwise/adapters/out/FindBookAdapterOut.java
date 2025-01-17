package io.bookwise.adapters.out;

import io.bookwise.adapters.out.client.InventoryManagementClient;
import io.bookwise.adapters.out.mapper.BookMapper;
import io.bookwise.adapters.out.mapper.InventoryManagementMapper;
import io.bookwise.adapters.out.repository.BookRepository;
import io.bookwise.application.core.domain.Book;
import io.bookwise.application.core.ports.out.FindBookPortOut;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class FindBookAdapterOut implements FindBookPortOut {

    private final BookRepository repository;

    private final InventoryManagementClient serviceClient;
    private final InventoryManagementMapper mapper;

    @Override
    public Optional<Book> findIsbn(String isbn) {
        log.info("Finding book by isbn: {}", isbn);

        var request = mapper.mapToSearchBookRequest(isbn);
        var response = serviceClient.findByIsbn(request);

        var book = Optional.ofNullable(mapper.mapToBookDomain(response));

        return book;
    }

    @Override
    public List<Book> findAllByCategory(String category) {
        return repository.findAllByCategory(category).stream().map(BookMapper::toDomain).toList();
    }

}