package io.bookwise.adapters.out.command;

import io.bookwise.adapters.out.client.InventoryManagementClient;
import io.bookwise.adapters.out.mapper.InventoryManagementMapper;
import io.bookwise.application.core.domain.Book;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateBookCommand implements InventoryCommand {

    private final InventoryManagementClient inventoryManagementClient;

    private final InventoryManagementMapper inventoryManagementMapper;

    private final Book book;

    @Override
    public void execute() {
        var bookRequest = inventoryManagementMapper.mapToBookRequest(book);
        inventoryManagementClient.saveBook(bookRequest);
    }

}