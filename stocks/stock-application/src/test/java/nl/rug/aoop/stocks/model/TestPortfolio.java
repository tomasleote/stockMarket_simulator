package nl.rug.aoop.stocks.model;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestPortfolio {

    private Portfolio portfolio;

    @Before
    public void setUp() {
        portfolio = new Portfolio();
    }

    @Test
    public void testAddStock() {
        portfolio.addStock("AAPL", 100);
        assertEquals(100, portfolio.getOwnedShares().get("AAPL").intValue());

        // Adding more shares of the same stock
        portfolio.addStock("AAPL", 50);
        assertEquals(150, portfolio.getOwnedShares().get("AAPL").intValue());
    }

    @Test
    public void testRemoveStock() {
        // Add stocks first
        portfolio.addStock("GOOGL", 200);
        portfolio.addStock("MSFT", 150);

        // Remove some shares
        portfolio.removeStock("GOOGL", 100);
        assertEquals(100, portfolio.getOwnedShares().get("GOOGL").intValue());

        // Try to remove more shares than available
        portfolio.removeStock("MSFT", 200);
        assertTrue(portfolio.getOwnedShares().containsKey("MSFT"));
        assertEquals(150, portfolio.getOwnedShares().get("MSFT").intValue());

        // Remove all shares of a stock
        portfolio.removeStock("MSFT", 150);
        assertFalse(portfolio.getOwnedShares().containsKey("MSFT"));
    }

    @Test
    public void testGetNamesOfOwnedStocks() {
        // Add some stocks
        portfolio.addStock("AAPL", 100);
        portfolio.addStock("GOOGL", 200);
        portfolio.addStock("MSFT", 150);

        // Get names of owned stocks
        assertEquals(3, portfolio.getNamesOfOwnedStocks().size());
        assertTrue(portfolio.getNamesOfOwnedStocks().contains("AAPL"));
        assertTrue(portfolio.getNamesOfOwnedStocks().contains("GOOGL"));
        assertTrue(portfolio.getNamesOfOwnedStocks().contains("MSFT"));
    }

    @Test
    public void testToMessage() {
        Map<String, Integer> shares = new HashMap<>();
        shares.put("AAPL", 100);
        shares.put("AMD", 150);
        shares.put("GEL", 200);
        Portfolio portfolioWithShares = new Portfolio(shares);

        Portfolio portfolio = new Portfolio(portfolioWithShares.toMessage());
        assertEquals(portfolio.getNamesOfOwnedStocks(), portfolioWithShares.getNamesOfOwnedStocks());
        assertEquals(portfolio.getOwnedShares().get("AMD"), portfolioWithShares.getOwnedShares().get("AMD"));

    }
}
