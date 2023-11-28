package nl.rug.aoop.stocks.orders;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.stocks.model.StockExchange;
import nl.rug.aoop.stocks.stock.application.MarketUpdater;

import java.io.IOException;
import java.util.*;

/**
 * Class for limit order handler.
 */
@Getter
@Slf4j
public class OrdersHandler implements OrderHandler {

    private PriorityQueue<Order> bids;
    private PriorityQueue<Order> asks;
    private final MarketUpdater updater;

    /**
     * Constructor.
     * @param stockExchange the stockExchange.
     */
    public OrdersHandler(StockExchange stockExchange) {
        this.bids = new PriorityQueue<>((a, b) -> Double.compare(b.getPrice(), a.getPrice()));
        this.asks = new PriorityQueue<>(Comparator.comparingDouble(Order::getPrice));
        this.updater = new MarketUpdater(stockExchange);
    }

    /**
     * resolveOrder method.
     * @param buyOrder buyOrder.
     * @param sellOrder sellOrder.
     */
    private synchronized void resolveOrder(Order buyOrder, Order sellOrder) {

        int sellShares = sellOrder.getNoOfShares();
        int buyShares = buyOrder.getNoOfShares();
        double sellPrice = sellOrder.getPrice();

        // Check if seller has enough shares
        if (sellOrder.getTrader().getOwnedStocksInPortfolio().getOrDefault(sellOrder.getStock(), 0) < sellShares) {
            asks.remove(sellOrder);
            return;
        }

        // Check if buyer has enough funds
        if (buyOrder.getTrader().getFunds() < sellPrice * buyShares) {
            bids.remove(buyOrder);
            return;
        }

        if (buyOrder.getNoOfShares() > sellOrder.getNoOfShares()) {
            asks.remove(sellOrder);
            buyOrder.setNoOfShares(buyOrder.getNoOfShares() - sellOrder.getNoOfShares());
        } else {
            bids.remove(buyOrder);
            int diff = sellOrder.getNoOfShares() - buyOrder.getNoOfShares();
            if (diff != 0) {
                sellOrder.setNoOfShares(diff);
            }
        }
        updateStockExchange(buyOrder, sellOrder);
    }

    /**
     * Matches incoming buy order with sell orders on the asks list.
     * @param order Order to match.
     */
    private synchronized void matchBuyOrder(Order order) {
        try {
            double limitPrice = order.getPrice();
            while (!asks.isEmpty() && asks.peek().getPrice() <= limitPrice
                    && asks.peek().getStock().equals(order.getStock()) && order.getNoOfShares() > 0) {
                resolveOrder(order, Objects.requireNonNull(asks.poll()));
            }
            if (order.getNoOfShares() > 0) {
                bids.add(order);
            }
        } catch (NullPointerException e) {
            log.error("Exception occurred while matching buy order: " + e.getMessage());
        }
    }

    /**
     * Matches incoming sell order with buy orders on the bids list.
     * @param order Order to match.
     */
    private synchronized void matchSellOrder(Order order) {
        try {
            double limitPrice = order.getPrice();
            while (!bids.isEmpty() && bids.peek().getPrice() >= limitPrice && order.getNoOfShares() > 0 &&
                    bids.peek().getStock().equals(order.getStock())) {
                resolveOrder(Objects.requireNonNull(bids.poll()), order);
            }
            if (order.getNoOfShares() > 0) {
                asks.add(order);
            }
        } catch (NullPointerException e) {
            log.error("Exception occurred while matching sell order: " + e.getMessage());
        }
    }

    /**
     * Calls the updateMarker object and sends relevant information to update the stock market and traders.
     * @param buyOrder Buy order resolved.
     * @param sellOrder Sell order resolved.
     */
    public void updateStockExchange(Order buyOrder, Order sellOrder) {
        try {
            updater.update(buyOrder, sellOrder);
        } catch (IOException e) {
            log.error("Exception occurred while updating stock exchange: " + e.getMessage());
        }

    }

    /**
     * handleOrder method that matches the type of the order to the bids/asks.
     * @param order order to be handled.
     */
    @Override
    public void handleOrder(Order order) {

        if (order.getType().equals("buyMarket") || order.getType().equals("sellMarket")) {
            handleMarketOrder(order);
        } else if (order.getType().equals("buy")) {
            bids.add(order);
            matchBuyOrder(order);
        } else {
            asks.add(order);
            matchSellOrder(order);
        }
    }

    /**
     * Matches market order with best limit order.
     * @param marketOrder Market order to be matched.
     */
    private void handleMarketOrder(Order marketOrder) {
        if (marketOrder.getType().equals("buyMarket")) {
            matchMarketOrderWithBestLimitOrder(marketOrder, asks);
        } else if (marketOrder.getType().equals("sellMarket")) {
            matchMarketOrderWithBestLimitOrder(marketOrder, bids);
        }
    }

    /**
     * Matches market order with best limit order.
     * @param marketOrder Market order to be matched.
     * @param limitOrders Limit orders to be matched.
     */
    private void matchMarketOrderWithBestLimitOrder(Order marketOrder, PriorityQueue<Order> limitOrders) {
        Order bestLimitOrder = null;
        double bestPrice = (marketOrder.getType().equals("buyMarket")) ? Double.MAX_VALUE : 0;

        for (Order limitOrder : limitOrders) {
            if (limitOrder.getStock().equals(marketOrder.getStock())) {
                boolean isBetterPrice = (marketOrder.getType().equals("buyMarket")) ?
                        limitOrder.getPrice() < bestPrice :
                        limitOrder.getPrice() > bestPrice;
                if (isBetterPrice) {
                    bestLimitOrder = limitOrder;
                    bestPrice = limitOrder.getPrice();
                }
            }
        }

        if (bestLimitOrder != null) {
            marketOrder.setPrice(bestLimitOrder.getPrice()); // Set the market price
            if (marketOrder.getType().equals("buyMarket")) {
                matchBuyOrder(marketOrder);
            } else {
                matchSellOrder(marketOrder);
            }
        } else {
            log.info("No matching limit order found for market order of " + marketOrder.getStock());
        }
    }
}
