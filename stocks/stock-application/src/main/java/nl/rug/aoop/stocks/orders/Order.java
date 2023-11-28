package nl.rug.aoop.stocks.orders;

import nl.rug.aoop.stocks.model.Trader;

/**
 * Interface for orders.
 */
public interface Order {
    /**
     * Getter.
     * @return number of shares.
     */
    int getNoOfShares();

    /**
     * Getter.
     * @return stocks.
     */
    String getStock();

    /**
     * Getter.
     * @return type.
     */
    String getType();

    /**
     * Getter.
     * @return price.
     */
    Double getPrice();

    /**
     * Getter.
     * @return trader id.
     */
    Trader getTrader();

    /**
     * Setter.
     * @param noOfShares number of shares.
     */
    void setNoOfShares(int noOfShares);

    /**
     * toJson method.
     * @return the Order as String.
     */
    String toJson();

    /**
     * setPrice method that sets the price.
     * @param price to be set.
     */
    void setPrice(double price);
}
