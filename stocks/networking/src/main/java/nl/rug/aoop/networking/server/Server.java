package nl.rug.aoop.networking.server;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.networking.MessageHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Class for the main server.
 */
@Slf4j
@Getter
public class Server implements Runnable {
    private int port;

    private ServerSocket serverSocket;
    private ExecutorService executorService;
    private boolean running = false;
    private MessageHandler messageHandler;
    private List<ClientHandler> clientHandlers;
    private int connectedClients;
    private int id;

    /**
     * Constructor.
     * @param port where we establish a connection.
     * @param messageHandler handles messages.
     */
    public Server(int port, MessageHandler messageHandler) throws IOException {
        serverSocket = new ServerSocket(0);
        this.port = port;
        this.messageHandler = messageHandler;
        this.clientHandlers = Collections.synchronizedList(new ArrayList<>());
        executorService = Executors.newCachedThreadPool(); // Will create a new thread as needed
        connectedClients = 0;
        id = 0;
    }

    /**
     * Runs the server.
     */
    @Override
    public void run() {
        running = true;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            log.info("Server started on port: " + port);
            while (running) {
                try {// Continuously listen for client connections
                    Socket clientSocket = serverSocket.accept(); // Blocks until a client connects
                    log.info("Accepted connection from: " + clientSocket.getRemoteSocketAddress());
                    ClientHandler clientHandler = new ClientHandler(clientSocket, id, messageHandler);
                    clientHandlers.add(clientHandler);
                    connectedClients++;
                    executorService.submit(clientHandlers.get(id)); //  Spawn a new thread to handle the client
                    id++;
                } catch (IOException e) {
                    log.error("Socket error: ", e);
                }
            }

        } catch (IOException e) {
            log.error("Server error: " + e.getMessage());
        }
    }

    /**
     * Stops the server.
     */
    public void stop() {
        running = false;
        this.executorService.shutdown();
        log.info("Server stopped");
    }
}
