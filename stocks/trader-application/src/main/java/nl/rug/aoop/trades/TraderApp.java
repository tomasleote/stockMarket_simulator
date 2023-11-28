package nl.rug.aoop.trades;

import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.command.CommandHandler;
import nl.rug.aoop.command.CommandHandlerFactory;
import nl.rug.aoop.messagequeue.QueueMessageHandler;
import nl.rug.aoop.messagequeue.queues.ThreadSafeQueue;
import nl.rug.aoop.trades.commands.TraderCommandHandlerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * TraderApp class; It starts everything up needed for the trader app to run, implementing the facade pattern.
 */
@Slf4j
public class TraderApp {
    private static final int DEFAULT_PORT = 26969;
    private ThreadSafeQueue queue;
    private QueueMessageHandler traderMessageHandler;
    private int port;
    private TraderManager manager;
    private CommandHandlerFactory factory;
    private CommandHandler commandHandler;

    /**
     * Constructor for the traderApp that initialises the queue, the handler,  the manager and command handler.
     */
    public TraderApp() {
        queue = new ThreadSafeQueue();
        traderMessageHandler = new QueueMessageHandler(null);
        try {
            port = Integer.parseInt(System.getenv("MESSAGE_QUEUE_PORT"));
        } catch (NumberFormatException e) {
            port = DEFAULT_PORT;
            log.info("No environmental variable, starting server on default port");
        }

        try {
            manager = new TraderManager(new InetSocketAddress("Localhost", port), queue, traderMessageHandler);
            factory = new TraderCommandHandlerFactory(manager);
            commandHandler = factory.createCommandHandler();
            manager.getTraderMessageHandler().setCommandHandler(commandHandler);
        } catch (IOException e) {
            log.error(e.getLocalizedMessage());
        }
    }

    /**
     * method runTraderApp to start connections.
     */
    public void runTraderApp() {
        new Thread(manager).start();
        log.info("Trader Manager started");
    }
}
