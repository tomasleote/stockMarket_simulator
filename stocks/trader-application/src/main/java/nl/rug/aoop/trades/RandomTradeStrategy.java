package nl.rug.aoop.trades;

import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.stocks.model.Stock;
import nl.rug.aoop.stocks.model.StockRegistry;
import nl.rug.aoop.stocks.model.Trader;
import nl.rug.aoop.stocks.orders.LimitOrder;
import nl.rug.aoop.stocks.orders.MarketOrder;
import nl.rug.aoop.stocks.orders.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * RandomTradeStrategy class that implements TradeStrategy.
 */
@Slf4j
public class RandomTradeStrategy implements TradeStrategy {
    private Random random;
    private Trader trader;
    private StockRegistry stockRegistry;
    private TraderManager traderManager;

    /**
     * Constructor.
     * @param traderManager traderManager.
     */
    public RandomTradeStrategy(TraderManager traderManager) {
        this.random = new Random();
        this.traderManager = traderManager;
        this.stockRegistry = new StockRegistry();
    }

    /**
     * method that attempts to do trades; if the trader does not have funds it will only sell, and vice versa.
     * @param stockRegistry the stockRegistry to create orders from.
     * @param trader the trader doing the trade.
     * @return the created order.
     */
    @Override
    public Order trade(StockRegistry stockRegistry, Trader trader) {
        this.trader = trader;
        this.stockRegistry = stockRegistry;
        // Check if trader can trade
        if (trader.getFunds() <= 0 && trader.getOwnedStocks().isEmpty()) {
            return null;
        }
        int choice = random.nextInt(2);
        // Check if the trader does not have shares.
        if(trader.getOwnedStocks().isEmpty() && trader.getFunds() > 0) {
            return  buyStock(trader);
        // check if the trader does not have funds.
        } else if(trader.getFunds() <= 0 && !trader.getOwnedStocks().isEmpty()) {
            return sellStock(trader);
        }
        // if the trader has both funds and shares, then pick randomly.
        int orderTypeChoice = random.nextInt(10); // Random number between 0-9

        if (orderTypeChoice <= 5) {
            return (choice == 0 && trader.getFunds() > 0) ? buyStock(trader) : sellStock(trader);
        } else {
            return (choice == 0 && trader.getFunds() > 0) ? buyMarketOrder(trader) : sellMarketOrder(trader);
        }
    }

    /**
     * Method that creates a buy order.
     * @param trader that does the order.
     * @return the offer.
     */
    public Order buyMarketOrder(Trader trader) {
        Stock selectedStock = stockRegistry.getStocks().get(chooseRandomStock());
        if (selectedStock == null) {
            return null;
        }
        double stockLimitTraderCanBuy = trader.getFunds() / selectedStock.getPrice();
        int chosenAmount = (stockLimitTraderCanBuy > 1) ? random.nextInt((int) stockLimitTraderCanBuy ) : 0;

        return new MarketOrder(trader, "buyMarket", chosenAmount, selectedStock.getSymbol());
    }

    /**
     * Method that creates a sell order.
     * @param trader the trader that does the order.
     * @return the offer.
     */
    public Order sellMarketOrder(Trader trader) {
        List<String> ownedStocks = this.trader.getOwnedStocks();
        if (ownedStocks.isEmpty()) {
            return null;
        }

        String selectedStock = ownedStocks.get(random.nextInt(ownedStocks.size()));
        int ownedAmount = trader.getOwnedStocksInPortfolio().get(selectedStock);

        if (ownedAmount <= 0) {
            return null;
        }

        int chosenAmount = random.nextInt(ownedAmount);
        if (chosenAmount <= 0) {
            return null;
        }

        return new MarketOrder(trader, "sellMarket", chosenAmount, selectedStock);
    }

    /**
     * Chooses a random stock symbol from the stockRegistry.
     * @return A randomly selected stock symbol.
     */
    private String chooseRandomStock() {
        // Convert stock map keys to a list
        List<String> stockSymbols = new ArrayList<>(stockRegistry.getStocks().keySet());

        // Check if there are any stocks
        if(stockSymbols.isEmpty()) {
            return null;
        }

        // Return the randomly selected stock symbol
        return stockSymbols.get(random.nextInt(stockSymbols.size()));
    }

    /**
     * buyStock method that creates a buyOrder made by the given trader with random parameters.
     * @param trader to do the order.
     * @return the order created.
     */
    @Override
    public Order buyStock(Trader trader) {
        Stock selectedStock = stockRegistry.getStocks().get(chooseRandomStock());
        double priceOfSelectedStock;
        try {
            priceOfSelectedStock = selectedStock.getPrice();
        } catch (NullPointerException e) {
            log.error("The stock does not exist");
            return null;
        }
        double stockLimitTraderCanBuy = trader.getFunds() / priceOfSelectedStock;
        int chosenAmount = (stockLimitTraderCanBuy > 1) ? random.nextInt((int) stockLimitTraderCanBuy) : 0;
        if (chosenAmount <= 0) {
            return null;
        }
        String type = "buy";
        String selectedStockString = selectedStock.getSymbol();
        double newPrice = priceOfSelectedStock;
        double maxPrice = (5.0/100) * newPrice;
        if (maxPrice <= 0.1) {
            maxPrice = 0.1 + 0.01;
        }
        newPrice += random.nextDouble(0.1, maxPrice);
        Order order = new LimitOrder(trader, type, newPrice, chosenAmount, selectedStockString);
        log.info("buy: " + selectedStockString + " , amount" + chosenAmount);
        return order;
    }

    /**
     * sellStock command that creates a sellOrder made by the given trader with random parameters.
     * @param trader the given trader.
     * @return the order created.
     */
    @Override
    public Order sellStock(Trader trader) {
        List<String> ownedStocks = this.trader.getOwnedStocks();
        if (ownedStocks.isEmpty()) {
            return null;
        }
        String selectedStock = ownedStocks.get(random.nextInt(ownedStocks.size()));
        int ownedAmount = trader.getOwnedStocksInPortfolio().get(selectedStock);
        if (ownedAmount <= 0) {
            return null;
        }
        int chosenAmount = random.nextInt(ownedAmount + 1);
        if (chosenAmount <= 0) {
            return null;
        }
        Stock selectedStockModel = stockRegistry.getStockByName(selectedStock);
        if(selectedStockModel == null) {
            return null;
        }
        double currentPrice = selectedStockModel.getPrice();
        // Calculate the modified price for selling
        double maxPriceReduction = (5.0/100) * currentPrice;
        if (maxPriceReduction <= 0.1) {
            maxPriceReduction = 0.1 + 0.01;
        }
        double newPrice = currentPrice - random.nextDouble(0.1, maxPriceReduction);
        // Create and return the sell order
        Order order = new LimitOrder(trader, "sell", newPrice, chosenAmount, selectedStock);
        log.info("sell: " + order.toString());
        return order;
    }
}
