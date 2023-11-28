package nl.rug.aoop.trades.commands;

import nl.rug.aoop.command.Command;
import nl.rug.aoop.stocks.model.Stock;
import nl.rug.aoop.stocks.model.StockRegistry;
import nl.rug.aoop.stocks.stock.application.StockManager;
import nl.rug.aoop.trades.TraderManager;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

public class TestStocksUpdateCommand {
    private TraderManager traderManager;
    private StocksUpdateCommand stocksUpdateCommand;

    @Before
    public void setUp() {
        traderManager = mock(TraderManager.class);
        stocksUpdateCommand = new StocksUpdateCommand(traderManager);
    }

    @Test
    public void testExecute() {
        // Arrange
        StockRegistry stockRegistry = new StockRegistry();
        stockRegistry.addStocks("Stock", new Stock());
        Map<String, Object> options = new HashMap<>();
        options.put("body", stockRegistry.toJson());

        // Act
        stocksUpdateCommand.execute(options);

        // Assert
        verify(traderManager, times(1)).setStockRegistry(any(StockRegistry.class));
    }


}
