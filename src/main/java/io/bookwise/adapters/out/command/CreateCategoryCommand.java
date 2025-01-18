package io.bookwise.adapters.out.command;

import io.bookwise.adapters.out.client.InventoryManagementClient;
import io.bookwise.adapters.out.mapper.InventoryManagementMapper;
import io.bookwise.application.core.domain.Book;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateCategoryCommand implements InventoryCommand {

    private final InventoryManagementClient inventoryManagementClient;

    private final InventoryManagementMapper inventoryManagementMapper;

    private final Book book;

    @Override
    public void execute() {
        var categoryRequest = inventoryManagementMapper.mapToCategoryRequest(book.getCategory());
        var categoryResponse = inventoryManagementClient.saveCategory(categoryRequest);
        var categoryDomain = inventoryManagementMapper.mapToCategoryDomain(categoryResponse);
        book.setId(categoryDomain.getId());
    }

}