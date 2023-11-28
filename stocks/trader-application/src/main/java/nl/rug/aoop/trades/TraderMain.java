package nl.rug.aoop.trades;

import lombok.extern.slf4j.Slf4j;

/**
 * TraderMain class, responsible for adding traders and connecting to the StockMain.
 */
@Slf4j
public class TraderMain {

    /**
     * main method.
     * @param args args.
     */
    public static void main(String[] args) {
        TraderApp traderApp = new TraderApp();
        traderApp.runTraderApp();
    }
}