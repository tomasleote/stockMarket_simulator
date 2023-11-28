package nl.rug.aoop.stocks.commands;

import nl.rug.aoop.stocks.model.Stock;
import nl.rug.aoop.stocks.model.StockRegistry;
import nl.rug.aoop.stocks.orders.BuyOrder;
import nl.rug.aoop.stocks.orders.OrderHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

class TestBuyOrderCommand {

    BuyOrderCommand buyOrderCommand;
    OrderHandler orderHandler;

    @BeforeEach
    public void setUp() {
        orderHandler = mock(OrderHandler.class);

        Stock stockModel = new Stock();
        Map<String, Stock> stocks = new HashMap<>();
        stocks.put("tesla", stockModel);

        StockRegistry stockRegistry = new StockRegistry(stocks);

        buyOrderCommand = new BuyOrderCommand(orderHandler);
    }

    @Test
    void testExecute() {
        // Create a sample BuyOrder JSON string
        String buyOrderJson = "{\"id\": \"1\", \"type\": \"buy\", \"price\": 100.0, \"quantity\": 5, \"symbol\": \"tesla\"}";

        // Prepare the options map
        Map<String, Object> options = new HashMap<>();
        options.put("body", buyOrderJson);

        // Execute the command
        buyOrderCommand.execute(options);

        // Verify that handleOrder method is called with the correct BuyOrder object
        verify(orderHandler, times(1)).handleOrder(any(BuyOrder.class));
    }
}
