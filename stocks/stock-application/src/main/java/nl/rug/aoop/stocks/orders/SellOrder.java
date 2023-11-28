package nl.rug.aoop.stocks.orders;

import com.google.gson.JsonSyntaxException;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import com.google.gson.Gson;
import nl.rug.aoop.stocks.model.Trader;

/**
 * SellOrder class that implements Order.
 * This class is used to create a sell order.
 */
@Getter
@Setter
@Slf4j
public class SellOrder implements Order {

    private Trader trader;
    private String type;
    private Double price;
    private Integer noOfShares;
    private String stock;

    private static Gson gson;

    /**
     * Constructor.
     * @param trader trader of the order.
     * @param price minimum price that the trader wants to sell for.
     * @param noOfShares number of shares the trader wants to sell.
     * @param stock the stock the trader wants to sell.
     */
    public SellOrder(Trader trader, Double price, Integer noOfShares, String stock){
        this.stock = stock;
        this.price = price;
        this.type = "sell";
        this.noOfShares = noOfShares;
        this.trader = trader;
        this.gson = new Gson();
    }

    /**
     * toJson method.
     * Converts this instance of Order to a JsonString.
     * @return this instance of Order as a JsonString.
     */
    public String toJson() {
        return gson.toJson(this);
    }

    /**
     * setPrice method.
     * Sets the price of this instance of Order.
     * @param price price to set.
     */
    @Override
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * fromJson method.
     * Converts a JsonString to a SellOrder.
     * @param string sellOrder as a JsonString.
     * @return sellOrder from the given JsonString.
     */
    public static SellOrder fromJson(String string) {
        try {
            gson = new Gson();
            return gson.fromJson(string, SellOrder.class);
        } catch (JsonSyntaxException e) {
            log.error("Error in converting SellOrder from Json: " + e.getMessage());
        }
        return null;
    }

    /**
     * getStock method.
     * @return the stock.
     */
    @Override
    public String getStock() {
        return stock;
    }

    /**
     * getType method.
     * @return the type.
     */
    @Override
    public String getType() {
        return type;
    }

    /**
     * getPrice method.
     * @return the price.
     */
    @Override
    public Double getPrice() {
        return price;
    }

    /**
     * getTrader method.
     * @return the trader.
     */
    @Override
    public Trader getTrader() {
        return trader;
    }

    /**
     * setNoOfShares method.
     * @param noOfShares number of shares to set.
     */
    @Override
    public void setNoOfShares(int noOfShares) {
        this.noOfShares = noOfShares;
    }

    /**
     * getNoOfShares method.
     * @return the number of shares.
     */
    @Override
    public int getNoOfShares() {
        return noOfShares;
    }
}
