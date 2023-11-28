package nl.rug.aoop.stocks.stock.application;

import nl.rug.aoop.stocks.model.*;
import nl.rug.aoop.stocks.orders.BuyOrder;
import nl.rug.aoop.stocks.orders.SellOrder;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class TestMarketUpdater {
    private MarketUpdater marketUpdater;
    private StockExchange stockExchange;

    @Test
    public void testUpdate() throws IOException {
        StockRegistry stockRegistry = new StockRegistry();
        TraderRegistry traderRegistry = new TraderRegistry();
        // Create mock traders and stocks
        Map<String, Integer> shares = new HashMap<>();
        shares.put("ABC", 50);
        Portfolio sellerPortfolio = new Portfolio(shares);
        Trader seller = new Trader("sellerId", "Seller", 100, sellerPortfolio, 0);
        Trader buyer = new Trader("buyerId", "Buyer", 100, new Portfolio(), 1);
        Stock stock = new Stock("ABC", "abece", 500L, 500);

        stockRegistry.addStocks("ABC", stock);
        traderRegistry.addTrader(seller);
        traderRegistry.addTrader(buyer);

        // Create mock orders
        SellOrder sellOrder = new SellOrder(seller, 100.0, 50, "ABC");
        BuyOrder buyOrder = new BuyOrder(buyer, 101.0, 50, "ABC");

        stockExchange = new StockExchange(traderRegistry, stockRegistry);
        marketUpdater = new MarketUpdater(stockExchange);

        marketUpdater.update(buyOrder, sellOrder);

        // Assert that the stock price is updated correctly
        assertEquals(100.0, stock.getInitialPrice(), 0.01);

        // Assert that the seller's portfolio and funds are updated correctly
        assertEquals(200, seller.getFunds(), 0.01);
        assertEquals(0, seller.getStockPortfolio().getNamesOfOwnedStocks().size());

        // Assert that the buyer's portfolio and funds are updated correctly
        assertEquals(0, buyer.getFunds(), 0.01);
        assertEquals(1, buyer.getStockPortfolio().getNamesOfOwnedStocks().size());
    }
}
