package nl.rug.aoop.trades;

import nl.rug.aoop.stocks.model.StockRegistry;
import nl.rug.aoop.stocks.model.Trader;
import nl.rug.aoop.stocks.orders.Order;

/**
 * TradeStrategy interface.
 */
public interface TradeStrategy {
    /**
     * trade method.
     * @param stockRegistry stockRegistry.
     * @param trader traderRegistry.
     * @return order.
     */
    Order trade(StockRegistry stockRegistry, Trader trader);

    /**
     * buyStock method.
     * @param trader trader.
     * @return order.
     */
    Order buyStock(Trader trader);

    /**
     * sellStock method.
     * @param trader trader.
     * @return order.
     */
    Order sellStock(Trader trader);
}
