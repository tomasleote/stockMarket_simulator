package nl.rug.aoop.stocks.stock.application;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;


/**
 * Main class.
 * This class is used to run the stock application.
 */
@Slf4j
public class StockMain {

    /**
     * Main function.
     * @param args args.
     * @throws IOException exception.
     */
    public static void main(String[] args) throws IOException {
        StockApp stockApp = new StockApp();
        stockApp.runStockApp();
    }
}