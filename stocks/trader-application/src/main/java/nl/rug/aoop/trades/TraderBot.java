package nl.rug.aoop.trades;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.messagequeue.Message;
import nl.rug.aoop.messagequeue.users.MQProducer;
import nl.rug.aoop.messagequeue.users.NetworkProducer;
import nl.rug.aoop.networking.client.Client;
import nl.rug.aoop.stocks.model.Trader;
import nl.rug.aoop.stocks.orders.Order;

import java.util.Random;

/**
 * TraderBot class.
 */
@Slf4j
@Setter
@Getter
public class TraderBot implements Runnable {
    private static final int CONNECTION_TIMEOUT = 1000;
    private static final int DEFAULT_PORT = 6969;
    private Client client;
    private boolean connected, running;
    private Trader trader;
    private TraderManager traderManager;
    private TradeStrategy tradeStrategy;
    private MQProducer mqProducer;
    private Random random;

    /**
     * Constructor.
     * @param client the corresponding client to the bot.
     * @param trader the corresponding trader to the bot.
     * @param traderManager the traderManager.
     */
    public TraderBot(Client client, Trader trader, TraderManager traderManager) {
        this.trader = trader;
        this.client = client;
        this.traderManager = traderManager;
        this.mqProducer = new NetworkProducer(client);
        this.tradeStrategy = new RandomTradeStrategy(traderManager);
        random = new Random();
    }

    /**
     * requestUpdateTrader method that sends a message to request the updated trader information.
     */
    public void requestUpdateTrader() {
        Message message = new Message("UpdateTrader", trader.getId());
        mqProducer.put(message);
    }

    /**
     * requestUpdateStocks method that sends a message to request the updated stocks information.
     */
    public void requestUpdateStocks() {
        Message message = new Message("UpdateStocks", "");
        mqProducer.put(message);
    }

    /**
     * terminate method.
     */
    public void terminate() {
        running = false;
    }

    /**
     * sendOrder method that sends an order converted into a Message, with the body containing its type.
     */
    public void sendOrder() {
        Order order = tradeStrategy.trade(traderManager.getStockRegistry(), trader);
        if (order != null) {
            String header = order.getType();
            String body = order.toJson();
            Message message = new Message(header, body);
            mqProducer.put(message);
        }
    }

    /**
     * updateTrader method that updates the given trader.
     * @param trader given trader.
     */
    public void updateTrader(Trader trader) {
        this.trader = trader;
    }

    /**
     * run method that waits for a random amount of time, then requests updates and sends an order.
     */
    @Override
    public void run() {
        running = true;
        while(running) {
            try {
                Thread.sleep(random.nextInt(250, 1500));
                requestUpdateStocks();
                requestUpdateTrader();
                Thread.sleep(250);
                sendOrder();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
