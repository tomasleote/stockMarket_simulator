package nl.rug.aoop.stocks.orders;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;
import nl.rug.aoop.stocks.model.Trader;

/**
 * Class for Limit order.
 */
@Getter
@Setter
public class LimitOrder implements Order{
    private String traderId;
    private String type;
    private Double price;
    private Integer noOfShares;
    private String stock;
    private static Gson g;
    private Trader trader;

    /**
     * Constructor.
     * @param trader trader of order.
     * @param type type of order.
     * @param price price.
     * @param noOfShares number of shares.
     * @param stock stock.
     */
    public LimitOrder(Trader trader,String type, Double price, Integer noOfShares, String stock){
        this.stock = stock;
        this.price = price;
        this.type = type;
        this.noOfShares = noOfShares;
        this.trader = trader;
        g = new Gson();
    }

    /**
     * getNoOfShares method.
     * @return the number of shares.
     */
    @Override
    public int getNoOfShares() {
        return noOfShares;
    }

    /**
     * getStock method.
     * @return the stock of the order.
     */
    @Override
    public String getStock() {
        return stock;
    }

    /**
     * getType method.
     * @return the type of the order.
     */
    @Override
    public String getType() {
        return type;
    }

    /**
     * getPrice method.
     * @return the price of the order.
     */
    @Override
    public Double getPrice() {
        return price;
    }

    /**
     * getTrader method.
     * @return the trader of the order.
     */
    @Override
    public Trader getTrader() {
        return trader;
    }

    /**
     * setNoOfShares method.
     * @param noOfShares number of shares.
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
     * toJson method.
     * @return this instance of the class as a JsonString.
     */
    @Override
    public String toJson() {
        return g.toJson(this);
    }

    /**
     * Converts Json string to a BuyOrder Object.
     * @param str order as Json string.
     * @return BuyOrder.
     */
    public static LimitOrder fromJson(String str) {
        return g.fromJson(str, LimitOrder.class);
    }

}