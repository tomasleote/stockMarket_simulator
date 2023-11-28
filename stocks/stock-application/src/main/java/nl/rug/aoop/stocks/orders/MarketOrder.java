package nl.rug.aoop.stocks.orders;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;
import nl.rug.aoop.stocks.model.Trader;

/**
 * Class for Market order.
 */
@Getter
@Setter
public class MarketOrder implements Order {
    private String traderId;
    private String type;
    private Double price;
    private Integer noOfShares;
    private String stock;
    private static Gson g;
    private Trader trader;

    /**
     * Constructor.
     * @param trader Trader of order.
     * @param type Type of order.
     * @param noOfShares Number of shares.
     * @param stock Stock.
     */
    public MarketOrder(Trader trader, String type, Integer noOfShares, String stock){
        this.trader = trader;
        this.type = type;
        this.noOfShares = noOfShares;
        this.stock = stock;
        this.price = 0.0; // Initially set to 0, will be updated at execution
        g = new Gson();
    }

    /**
     * getNoOfShares method, gets the number of shares, as the name suggests.
     * @return the number of shares of the order.
     */
    @Override
    public int getNoOfShares() {
        return noOfShares;
    }

    /**
     * getStock method, gets the stock of the order, as the name suggests.
     * @return the stock of the order.
     */
    @Override
    public String getStock() {
        return stock;
    }

    /**
     * getType method, gets the type of the order, as the name suggests.
     * @return the type of the order.
     */
    @Override
    public String getType() {
        return type;
    }

    /**
     * getPrice method, gets the price of the order, as the name suggests.
     * @return the price of the order.
     */
    @Override
    public Double getPrice() {
        return price;
    }

    /**
     * getTrader method, gets the trader of the order, as the name suggests.
     * @return the trader of the order.
     */
    @Override
    public Trader getTrader() {
        return trader;
    }

    /**
     * setNoOfShares method, sets the number of shares of the order, as the name suggests.
     * @param noOfShares new number of shares.
     */
    @Override
    public void setNoOfShares(int noOfShares) {
        this.noOfShares = noOfShares;
    }

    /**
     * sets the price of this current order.
     * @param price new price.
     */
    @Override
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Converts MarketOrder to Json string.
     * @return MarketOrder as Json string.
     */
    @Override
    public String toJson() {
        return g.toJson(this);
    }

    /**
     * Converts Json string to a MarketOrder Object.
     * @param str Order as Json string.
     * @return MarketOrder.
     */
    public static MarketOrder fromJson(String str) {
        return g.fromJson(str, MarketOrder.class);
    }
}
