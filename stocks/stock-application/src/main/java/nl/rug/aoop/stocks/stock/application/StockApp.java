package nl.rug.aoop.stocks.stock.application;

import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.command.CommandHandler;
import nl.rug.aoop.command.CommandHandlerFactory;
import nl.rug.aoop.initialization.SimpleViewFactory;
import nl.rug.aoop.messagequeue.QueueMessageHandler;
import nl.rug.aoop.messagequeue.queues.ThreadSafeQueue;
import nl.rug.aoop.networking.server.Server;
import nl.rug.aoop.stocks.commands.StockCommandHandlerFactory;
import nl.rug.aoop.stocks.model.StockExchange;
import nl.rug.aoop.stocks.model.StockRegistry;
import nl.rug.aoop.stocks.model.TraderRegistry;
import nl.rug.aoop.stocks.orders.OrderHandler;
import nl.rug.aoop.stocks.orders.OrdersHandler;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * StockApp class which is used to initialize the stock app and start it in a simple way.
 */
@Slf4j
public class StockApp {
    private static final int DEFAULT_PORT = 26969;
    private static Server server;
    private static final ExecutorService EXECUTOR_SERVICE = Executors.newCachedThreadPool();
    private final TraderUpdater traderUpdater;
    private final StockRegistry stockRegistry;
    private final StockExchange stockExchange;
    private final OrderHandler handler;
    private final QueueMessageHandler messageHandler;
    private final StockManager stockManager;
    private final SimpleViewFactory sWFactory;

    /**
     * Constructor of the StockApp class that initializes everything.
     * @throws IOException potentially thrown by the Server constructor.
     */
    public StockApp() throws IOException {
        TraderRegistry traderRegistry = new TraderRegistry();
        traderUpdater = new TraderUpdater(null, null, traderRegistry);
        stockRegistry = new StockRegistry();
        stockExchange = new StockExchange(traderRegistry, stockRegistry);
        handler = new OrdersHandler(stockExchange);
        messageHandler = new QueueMessageHandler(null);
        ThreadSafeQueue queue = new ThreadSafeQueue();

        int port = 0;
        try {
            port = Integer.parseInt(System.getenv("MESSAGE_QUEUE_PORT"));
            log.info("Starting server on defined port");
        } catch (NumberFormatException e) {
            port = DEFAULT_PORT;
            log.info("No environmental variable, starting server on default port");
        }
        server = new Server(port, messageHandler);
        stockManager = new StockManager(handler, stockExchange, queue, server, traderUpdater);
        initStockManager();
        sWFactory = new SimpleViewFactory();
    }

    /**
     * Method that initializes the stock manager by loading the stocks and traders,
     * as well as creating a command handler.
     */
    private void initStockManager() {
        stockManager.loadStocks();
        stockManager.logStocks();
        stockManager.loadTraders();

        CommandHandlerFactory factory =
                new StockCommandHandlerFactory(stockManager, handler);
        CommandHandler commandHandler = factory.createCommandHandler();
        messageHandler.setCommandHandler(commandHandler);
    }

    /**
     * Method to run the stock app, starts a new server thread
     * and is given the stockManager and Trader updater through the executor service.
     */
    public void runStockApp() {
        new Thread(server).start();
        log.info("Server started");
        EXECUTOR_SERVICE.submit(stockManager);
        sWFactory.createView(stockExchange);
        EXECUTOR_SERVICE.submit(traderUpdater);
    }
}
