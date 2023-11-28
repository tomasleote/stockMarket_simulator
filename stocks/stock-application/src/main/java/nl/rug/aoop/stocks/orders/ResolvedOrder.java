package nl.rug.aoop.stocks.orders;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;
import nl.rug.aoop.stocks.model.Trader;

/**
 * Class for resolved orders.
 */
@Getter
@Setter
public class ResolvedOrder implements Order{
    private Trader trader;
    private String type;
    private Double price;
    private Integer noOfShares;
    private String stocks;
    private Double remainingPrice;
    private Integer remainingShares;
    private Gson g;

    /**
     * Constructor.
     * @param trader          trader.
     * @param type            type.
     * @param price           price.
     * @param noOfShares      number of shares.
     * @param stocks          stocks.
     * @param remainingPrice  remaining price.
     * @param remainingShares remaining shares.
     */
    public ResolvedOrder(Trader trader, String type,double price,
                         Integer noOfShares, String stocks, Double remainingPrice, Integer remainingShares){
        this.stocks = stocks;
        this.price = price;
        this.type = type;
        this.noOfShares = noOfShares;
        this.trader = trader;
        this.remainingPrice = remainingPrice;
        this.remainingShares = remainingShares;
        g = new Gson();
    }

    /**
     * Getter.
     * @return number of shares.
     */
    @Override
    public int getNoOfShares() {
        return noOfShares;
    }

    /**
     * Getter.
     * @return stocks.
     */
    @Override
    public String getStock() {
        return stocks;
    }

    /**
     * Getter.
     * @return type.
     */
    @Override
    public String getType() {
        return type;
    }

    /**
     * Getter.
     * @return price.
     */
    @Override
    public Double getPrice() {
        return price;
    }

    /**
     * Getter.
     * @return trader id.
     */
    @Override
    public Trader getTrader() {
        return trader;
    }

    /**
     * Setter.
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
     * @return resolvedOrder as JsonString.
     */
    @Override
    public String toJson() {
        return g.toJson(this);
    }
}
