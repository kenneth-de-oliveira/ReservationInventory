package io.bookwise.adapters.out.invoker;

import io.bookwise.adapters.out.command.InventoryCommand;
import java.util.ArrayList;
import java.util.List;

public class InventoryManagementInvoker {

    private final List<InventoryCommand> commands = new ArrayList<>();

    public void addCommand(InventoryCommand command) {
        commands.add(command);
    }

    public void executeCommands() {
        commands.forEach(InventoryCommand::execute);
    }

}