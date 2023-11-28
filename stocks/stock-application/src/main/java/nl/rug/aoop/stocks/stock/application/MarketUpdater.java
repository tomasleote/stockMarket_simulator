package nl.rug.aoop.stocks.stock.application;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.stocks.model.*;
import nl.rug.aoop.stocks.orders.Order;

import java.io.IOException;

/**
 * Class to update the market.
 * This class is used to update the stocks and traders involved in a transaction.
 */
@Slf4j
@Getter
@Setter
public class MarketUpdater {

    private StockExchange stockExchange;

    /**
     * Constructor.
     * @param stockExchange stockExchange.
     */
    public MarketUpdater(StockExchange stockExchange){
        this.stockExchange = stockExchange;
    }

    /**
     * Method that updates the stocks and traders involved.
     * @param buyOrder buyOrder.
     * @param sellOrder sellOrder.
     */
    public void update(Order buyOrder, Order sellOrder) throws IOException {
        //Sets new price of the stock
        Stock stock = stockExchange.getStockRegistry().getStocks().get(sellOrder.getStock());
        Trader traderSelling = stockExchange.getTraderRegistry().getTraders().get(sellOrder.getTrader().getId());
        Trader traderBuying = stockExchange.getTraderRegistry().getTraders().get(buyOrder.getTrader().getId());
        stock.setInitialPrice(sellOrder.getPrice());

        //Updates the traders
        String sellerID = traderSelling.getId();
        String buyerID = traderBuying.getId();

        //Remove stock from seller portfolio and add funds to wallet
        traderSelling.getStockPortfolio().removeStock(sellOrder.getStock(), sellOrder.getNoOfShares());
        traderSelling.setFunds(stockExchange.getTraderRegistry().getTraders().get(sellerID).getFunds()
                + sellOrder.getPrice());

        // Add stock to buyer portfolio and remove funds from the wallet
        traderBuying.getStockPortfolio().addStock(buyOrder.getStock(), buyOrder.getNoOfShares());
        traderBuying.setFunds(stockExchange.getTraderRegistry().getTraders().get(buyerID).getFunds()
                - sellOrder.getPrice());
    }
}
