package nl.rug.aoop.stocks.model;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.model.StockDataModel;
import nl.rug.aoop.model.StockExchangeDataModel;
import nl.rug.aoop.model.TraderDataModel;

/**
 * StockExchange class that implements StockExchangeDataModel.
 */
@Slf4j
@Getter
@Setter
public class StockExchange implements StockExchangeDataModel {
    private StockRegistry stockRegistry;
    private TraderRegistry traderRegistry;

    /**
     * Constructor of the class.
     * @param traderRegistry the traderRegistry to do the exchange on.
     * @param stockRegistry the stockRegistry to do the exchange on.
     */
    public StockExchange(TraderRegistry traderRegistry, StockRegistry stockRegistry) {
        this.traderRegistry = traderRegistry;
        this.stockRegistry = stockRegistry;
    }

    /**
     * getStockByIndex method, pretty self explanatory name, gets a stock by its index.
     * @param index The index of the stock that should be accessed.
     * @return the stock at the index.
     */
    @Override
    public StockDataModel getStockByIndex(int index) {
        int counter = 0;
        for ( Stock stock : stockRegistry.getStocks().values()) {
            if (counter == index) {
                return stock;
            }
            counter++;
        }
        return null;
    }

    /**
     * getNumberOfStocks method, gets the total number of stocks in the registry, self explanatory name.
     * @return the number of stocks in the stockRegistry.
     */
    @Override
    public int getNumberOfStocks() {
        return stockRegistry.getStocks().size();
    }

    /**
     * getTraderByIndex method, gets the trader by its index, self explanatory name.
     * @param index The index of the trader that should be accessed.
     * @return the trader at the index.
     */
    @Override
    public TraderDataModel getTraderByIndex(int index) {
        int counter = 0;
        for ( Trader trader : traderRegistry.getTraders().values()) {
            if (counter == index) {
                return trader;
            }
            counter++;
        }
        return null;
    }

    /**
     * getNumberOfTraders method, gets the number of traders in the registry, as the name suggests.
     * @return the number of traders in the registry.
     */
    @Override
    public int getNumberOfTraders() {
        return traderRegistry.getTraders().size();
    }
}