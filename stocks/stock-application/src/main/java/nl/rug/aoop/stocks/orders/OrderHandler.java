package nl.rug.aoop.stocks.orders;

/**
 * Interface for order hanndlers.
 */
public interface OrderHandler {
    /**
     * Resolves orders.
     * @param order order.
     */
    void handleOrder(Order order);
}
