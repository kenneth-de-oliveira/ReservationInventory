package io.bookwise.adapters.out;

import io.bookwise.adapters.out.client.InventoryManagementClient;
import io.bookwise.adapters.out.command.CreateBookCommand;
import io.bookwise.adapters.out.command.CreateCategoryCommand;
import io.bookwise.adapters.out.invoker.InventoryManagementInvoker;
import io.bookwise.adapters.out.mapper.InventoryManagementMapper;
import io.bookwise.application.core.domain.Book;
import io.bookwise.application.core.ports.out.CreateBookPortOut;
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