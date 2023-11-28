package nl.rug.aoop.stocks.orders;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.stocks.model.Trader;

/**
 * BuyOrder class that implements Order.
 */
@Getter
@Setter
@Slf4j
public class BuyOrder implements Order{

    private Trader trader;
    private String type;
    private Double price;
    private Integer noOfShares;
    private String stock;

    private static Gson gson;

    /**
     * Constructor.
     * @param trader the trader doing the order.
     * @param price the maximum price the trader wants to buy for.
     * @param noOfShares the number of shares the trader wants to buy.
     * @param stock the stock the trader wants to buy.
     */
    public BuyOrder(Trader trader, Double price, Integer noOfShares, String stock){
        this.trader = trader;
        this.price = price;
        this.noOfShares = noOfShares;
        this.stock = stock;
        this.type = "buy";
        this.gson = new Gson();
    }

    /**
     * toJson method.
     * @return this instance as a JsonString.
     */
    public String toJson() {
        return gson.toJson(this);
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
     * fromJson method.
     * @param string the order as a JsonString to be converted to BuyOrder.
     * @return the BuyOrder.
     */
    public static BuyOrder fromJson(String string) {
        try {
            gson = new Gson();
            return gson.fromJson(string, BuyOrder.class);
        } catch (JsonSyntaxException e) {
            log.error("Error in converting BuyOrder from Json: " + e.getMessage());
        }
        return null;
    }

    /**
     * getStock method.
     * @return the stock of the order.
     */
    public String getStock() {
        return stock;
    }

    /**
     * getType method.
     * @return the type of de order as a String.
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
     * @return the trader.
     */
    @Override
    public Trader getTrader() {
        return trader;
    }

    /**
     * setNoOfShares method.
     * @param noOfShares number of shares to be set.
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
