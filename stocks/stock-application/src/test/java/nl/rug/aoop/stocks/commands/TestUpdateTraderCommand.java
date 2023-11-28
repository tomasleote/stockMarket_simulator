package nl.rug.aoop.stocks.commands;

import nl.rug.aoop.stocks.stock.application.StockManager;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

public class TestUpdateTraderCommand {
    private StockManager stockManager = mock(StockManager.class);
    public UpdateTraderCommand updateTraderCommand = new UpdateTraderCommand(stockManager);

    @Test
    public void testExecute() {
        // Arrange
        Map<String, Object> options = new HashMap<>();
        String traderId = "12345";
        options.put("body", traderId);

        // Act
        updateTraderCommand.execute(options);

        // Assert
        verify(stockManager, times(1)).sendTrader(traderId);
    }
}
