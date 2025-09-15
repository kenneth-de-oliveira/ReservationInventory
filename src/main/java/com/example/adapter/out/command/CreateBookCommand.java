package com.example.adapter.out.command;

import com.example.adapter.out.client.InventoryManagementClient;
import com.example.adapter.out.mapper.InventoryManagementMapper;
import com.example.application.core.domain.Book;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateBookCommand implements InventoryCommand {

    private final InventoryManagementClient inventoryManagementClient;

    private final InventoryManagementMapper inventoryManagementMapper;

    private final Book book;

    @Override
    public void execute() {
        var bookRequest = inventoryManagementMapper.toBookRequest(book);
        inventoryManagementClient.saveBook(bookRequest);
    }

}