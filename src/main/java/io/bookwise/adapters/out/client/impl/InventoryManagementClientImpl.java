package io.bookwise.adapters.out.client.impl;

import com.example.inventorymanagement.*;
import com.google.gson.Gson;
import feign.FeignException;
import io.bookwise.adapters.out.client.BIMServiceClient;
import io.bookwise.adapters.out.client.InventoryManagementClient;
import io.bookwise.adapters.out.client.dto.RetrieveAllBooksRequest;
import io.bookwise.framework.errors.GenericErrorsEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

@Slf4j
@Component
@RequiredArgsConstructor
public class InventoryManagementClientImpl implements InventoryManagementClient {

    private final Gson gson;
    private final BIMServiceClient serviceClient;

    @Override
    public BookResponse findByIsbn(SearchBookRequest request) {
        return handleExceptions(
                () -> Optional.ofNullable(serviceClient.findByIsbn(request))
                        .stream()
                        .peek(response -> log.debug("Book found by isbn: {}", gson.toJson(response)))
                        .findFirst()
                        .orElse(null),
                ex -> {
                    if (ex instanceof FeignException && ex.getMessage().contains("Book not found")) {
                        handleMessageException("Book not found: ", ex);
                        throw new RuntimeException("Book not found");
                    }
                    if (ex instanceof FeignException) {
                        handleMessageException("Error finding Book by isbn: ", ex);
                        throw new RuntimeException("Error finding Book by isbn: " + ex.getMessage());
                    }
                    handleMessageException(GenericErrorsEnum.ERROR_GENERIC.getInfo(), ex);
                    throw new RuntimeException(GenericErrorsEnum.ERROR_GENERIC.getInfo());
                }
        );
    }

    @Override
    public BookResponse findAll() {
        return handleExceptions(
                () -> Stream.of(serviceClient.findAll(new RetrieveAllBooksRequest()))
                        .peek(response -> log.info("Books found: {}", gson.toJson(response)))
                        .findFirst()
                        .orElse(null),
                ex -> {
                    if (ex instanceof FeignException && ex.getMessage().contains("No books found")) {
                        handleMessageException("No books found: ", ex);
                        throw new RuntimeException("No books found");
                    }
                    if (ex instanceof FeignException) {
                        handleMessageException("Error finding all books: ", ex);
                        throw new RuntimeException("Error finding all books: " + ex.getMessage());
                    }
                    handleMessageException(GenericErrorsEnum.ERROR_GENERIC.getInfo(), ex);
                    throw new RuntimeException(GenericErrorsEnum.ERROR_GENERIC.getInfo());
                }
        );
    }

    @Override
    public CategoryResponse saveCategory(CategoryRequest categoryRequest) {
        return handleExceptions(
                () -> Stream.of(serviceClient.createCategory(categoryRequest))
                        .peek(response -> log.info("Category created: {}", gson.toJson(response)))
                        .findFirst()
                        .orElse(null),
                ex -> {
                    if (ex instanceof FeignException) {
                        handleMessageException("Error creating category: ", ex);
                        throw new RuntimeException("Error creating category: " + ex.getMessage());
                    }
                    handleMessageException(GenericErrorsEnum.ERROR_GENERIC.getInfo(), ex);
                    throw new RuntimeException(GenericErrorsEnum.ERROR_GENERIC.getInfo());
                }
        );
    }

    @Override
    public void saveBook(BookRequest bookRequest) {
        handleExceptions(
                () -> {
                    serviceClient.createBook(bookRequest);
                    log.info("Book created: {}", gson.toJson(bookRequest));
                    return null;
                },
                ex -> {
                    if (ex instanceof FeignException && ex.getMessage().contains("Category not found")) {
                        handleMessageException("Category not found: ", ex);
                        throw new RuntimeException("Category not found");
                    }
                    if (ex instanceof FeignException) {
                        handleMessageException("Error creating book: ", ex);
                        throw new RuntimeException("Error creating book: " + ex.getMessage());
                    }
                    handleMessageException(GenericErrorsEnum.ERROR_GENERIC.getInfo(), ex);
                    throw new RuntimeException(GenericErrorsEnum.ERROR_GENERIC.getInfo());
                }
        );
    }

    private <T> T handleExceptions(Supplier<T> supplier, Consumer<Exception> exceptionHandler) {
        try {
            return supplier.get();
        } catch (Exception ex) {
            exceptionHandler.accept(ex);
            throw ex;
        }
    }

    private void handleMessageException(String msg, Exception ex) {
        log.error("{}: {}", msg, ex.getMessage());
    }

}