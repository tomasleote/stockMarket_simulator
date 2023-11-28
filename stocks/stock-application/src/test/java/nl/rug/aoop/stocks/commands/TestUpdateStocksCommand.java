package nl.rug.aoop.stocks.commands;

import nl.rug.aoop.stocks.commands.UpdateStocksCommand;
import nl.rug.aoop.stocks.stock.application.StockManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import static org.mockito.Mockito.*;

class TestUpdateStocksCommand {

    private StockManager stockManager;
    private UpdateStocksCommand updateStocksCommand;

    @BeforeEach
    void setUp() {
        stockManager = mock(StockManager.class);
        updateStocksCommand = new UpdateStocksCommand(stockManager);
    }

    @Test
    void testExecute() {
        Map<String, Object> options = new HashMap<>();

        updateStocksCommand.execute(options);

        verify(stockManager, times(1)).sendStocks();
    }
}
