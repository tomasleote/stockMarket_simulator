package nl.rug.aoop.stocks.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestStockRegistry {

    private StockRegistry stockRegistry;

    @Before
    public void setUp() {
        stockRegistry = new StockRegistry();
        stockRegistry.addStocks("AAPL", new Stock("AAPL", "Apple Inc", 1000000L, 150.50));
        stockRegistry.addStocks("GOOGL", new Stock("GOOGL", "Alphabet Inc", 500000L, 1200.75));
    }

    @Test
    public void testGetStockValues() {
        assertNotNull(stockRegistry.getStockValues());
        assertEquals(2, stockRegistry.getStockValues().size());
    }

    @Test
    public void testGetStockByName() {
        Stock stockModel = stockRegistry.getStockByName("AAPL");
        assertNotNull(stockModel);
        assertEquals("AAPL", stockModel.getSymbol());
        assertEquals("Apple Inc", stockModel.getName());
        assertEquals(1000000, stockModel.getSharesOutstanding());
        assertEquals(150.50, stockModel.getPrice(), 0.001);
    }

    @Test
    public void testAddStocks() {
        Stock newStock = new Stock("MSFT", "Microsoft Corporation", 2000000L, 300.60);
        stockRegistry.addStocks("MSFT", newStock);
        Stock retrievedStock = stockRegistry.getStockByName("MSFT");
        assertNotNull(retrievedStock);
        assertEquals("MSFT", retrievedStock.getSymbol());
        assertEquals("Microsoft Corporation", retrievedStock.getName());
        assertEquals(2000000, retrievedStock.getSharesOutstanding());
        assertEquals(300.60, retrievedStock.getPrice(), 0.001);
    }
}

