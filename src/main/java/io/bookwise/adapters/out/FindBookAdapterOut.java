package io.bookwise.adapters.out;

import io.bookwise.adapters.out.client.InventoryManagementClient;
import io.bookwise.adapters.out.mapper.InventoryManagementMapper;
import io.bookwise.application.core.domain.Book;
import io.bookwise.application.core.ports.out.FindBookPortOut;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Slf4j
@Component
@RequiredArgsConstructor
public class FindBookAdapterOut implements FindBookPortOut {

    private final InventoryManagementClient serviceClient;
    private final InventoryManagementMapper mapper;

    @Override
    public Optional<Book> findIsbn(String isbn) {
        log.info("Finding book by isbn: {}", isbn);

        return Optional.ofNullable(mapper.toSearchBookRequest(isbn))
                .map(serviceClient::findByIsbn)
                .map(mapper::toBookDomain);
    }

    @Override
    public List<Book> findAll() {
        return Stream.of(serviceClient.findAll())
                .map(mapper::toBookDomain)
                .toList();
    }

}