package com.example.adapter.out.command;

import com.example.adapter.out.client.InventoryManagementClient;
import com.example.adapter.out.mapper.InventoryManagementMapper;
import com.example.application.core.domain.Book;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateCategoryCommand implements InventoryCommand {

    private final InventoryManagementClient inventoryManagementClient;

    private final InventoryManagementMapper inventoryManagementMapper;

    private final Book book;

    @Override
    public void execute() {
        var categoryRequest = inventoryManagementMapper.toCategoryRequest(book.getCategory());
        var categoryResponse = inventoryManagementClient.saveCategory(categoryRequest);
        var categoryDomain = inventoryManagementMapper.toCategoryDomain(categoryResponse);
        book.setId(categoryDomain.getId());
    }

}