package nl.rug.aoop.stocks.commands;


import nl.rug.aoop.command.CommandHandler;
import nl.rug.aoop.stocks.model.StockRegistry;
import nl.rug.aoop.stocks.orders.OrderHandler;
import nl.rug.aoop.stocks.stock.application.StockManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class TestStockCommandHandlerFactory {
    private StockManager stockManager;
    private StockRegistry stockRegistry;
    private OrderHandler orderHandler;
    private StockCommandHandlerFactory commandHandlerFactory;

    @BeforeEach
    void setUp() {
        stockManager = mock(StockManager.class);
        stockRegistry = mock(StockRegistry.class);
        orderHandler = mock(OrderHandler.class);
        commandHandlerFactory = new StockCommandHandlerFactory(stockManager, orderHandler);
    }

    @Test
    void createCommandHandler_registersCommands() {

        CommandHandler commandHandler = commandHandlerFactory.createCommandHandler();

        assertNotNull(commandHandler);
    }
}