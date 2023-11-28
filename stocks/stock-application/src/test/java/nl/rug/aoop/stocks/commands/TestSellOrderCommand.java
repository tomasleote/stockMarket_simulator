package nl.rug.aoop.stocks.commands;

import nl.rug.aoop.stocks.orders.OrderHandler;
import nl.rug.aoop.stocks.orders.SellOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

class TestSellOrderCommand {

    SellOrderCommand sellOrderCommand;
    OrderHandler orderHandler;

    @BeforeEach
    public void setUp() {
        // Create a mock OrderHandler
        orderHandler = mock(OrderHandler.class);

        sellOrderCommand = new SellOrderCommand(orderHandler);
    }

    @Test
    void testExecute() {
        // Create a sample SellOrder JSON string
        String sellOrderJson = "{\"id\": \"1\", \"type\": \"sell\", \"price\": 150.0, \"quantity\": 3, \"symbol\": \"apple\"}";

        // Prepare the options map
        Map<String, Object> options = new HashMap<>();
        options.put("body", sellOrderJson);

        // Execute the command
        sellOrderCommand.execute(options);

        // Verify that handleOrder method is called with the correct SellOrder object
        verify(orderHandler, times(1)).handleOrder(any(SellOrder.class));
    }
}
