package com.example.adapter.out;

import com.example.adapter.out.client.InventoryManagementClient;
import com.example.adapter.out.command.CreateBookCommand;
import com.example.adapter.out.command.CreateCategoryCommand;
import com.example.adapter.out.invoker.InventoryManagementInvoker;
import com.example.adapter.out.mapper.InventoryManagementMapper;
import com.example.application.core.domain.Book;
import com.example.application.core.port.out.CreateBookPortOut;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreateBookAdapterOut implements CreateBookPortOut {

    private final InventoryManagementClient inventoryManagementClient;
    private final InventoryManagementMapper inventoryManagementMapper;

    @Override
    public void create(Book book) {
        log.info("Creating book: {}", book);

        var inventoryManagementInvoker = new InventoryManagementInvoker();
        inventoryManagementInvoker.addCommand(new CreateCategoryCommand(inventoryManagementClient, inventoryManagementMapper, book));
        inventoryManagementInvoker.addCommand(new CreateBookCommand(inventoryManagementClient, inventoryManagementMapper, book));
        inventoryManagementInvoker.executeCommands();

        log.info("Book created: {}", book);
    }

}