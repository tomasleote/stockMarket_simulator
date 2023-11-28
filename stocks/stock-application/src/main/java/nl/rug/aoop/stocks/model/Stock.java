package nl.rug.aoop.stocks.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.rug.aoop.messagequeue.Message;
import nl.rug.aoop.model.StockDataModel;

/**
 * Class for stock model.
 */
@NoArgsConstructor
@Getter
@Setter
public class Stock implements StockDataModel {
    private String symbol;
    private String name;
    private long sharesOutstanding;
    private double initialPrice;

    /**
     * Constructor from message object.
     * @param message message containing stock.
     */
    public Stock(Message message){
        symbol = message.getHeader();
        message = new Message(message.getBody());
        name = message.getHeader();
        message = new Message(message.getBody());
        sharesOutstanding = Long.parseLong(message.getHeader());
        initialPrice = Double.parseDouble(message.getBody());
    }

    /**
     * Constructor of this class.
     * @param symbol symbol of stock.
     * @param name name of stock.
     * @param sharesOutstanding shares outstanding of stock.
     * @param initialPrice initial price of stock.
     */
    public Stock(String symbol, String name, Long sharesOutstanding, double initialPrice) {
        this.symbol = symbol;
        this.name = name;
        this.sharesOutstanding = sharesOutstanding;
        this.initialPrice = initialPrice;
    }

    /**
     * Market cap of the stock.
     * @return the market cap of the stock.
     */
    @Override
    public double getMarketCap() {
        return (double) sharesOutstanding * initialPrice;
    }

    /**
     * Getter for the price of the stock.
     * @return price of the stock.
     */
    public double getPrice() {
        return initialPrice;
    }

    /**
     * Setter for the price of the stock..
     * @param newPrice new price of the stock.
     */
    public void setPrice(double newPrice) {
        this.initialPrice = newPrice;
    }

    /**
     * Creates message from object stock.
     * @return message of stock.
     */
    public Message toMessage() {
        Message sharesAndPrice = new Message(String.valueOf(sharesOutstanding), String.valueOf(initialPrice));
        Message nameMsg = new Message(name, sharesAndPrice.toJson());
        return new Message(symbol, nameMsg.toJson());
    }
}
