package nl.rug.aoop.networking.client;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.networking.Communicator;
import nl.rug.aoop.networking.MessageHandler;

import java.net.ConnectException;
import java.net.Socket;
import java.net.InetSocketAddress;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.io.IOException;

/**
 * Class for network client.
 */
@Slf4j
@Getter
public class Client implements Runnable, Communicator {

    private InetSocketAddress address;
    private Socket clientSocket;
    private String message;
    private PrintWriter out;
    private BufferedReader in;
    private MessageHandler messageHandler;
    private final int TIMEOUT = 10000;
    private boolean running = false;

    /**
     * Constructor for network cient.
     *
     * @param address Address of server.
     * @param handler Message handler.
     * @throws IOException exception thrown.
     */
    public Client(InetSocketAddress address, MessageHandler handler) throws IOException {
        this.address = address;
        this.messageHandler = handler;
        initSocket(address);
    }

    /**
     * Inits socket.
     * @param address Address of server.
     * @throws IOException exception thrown.
     */
    private void initSocket(InetSocketAddress address) throws IOException {
        clientSocket = new Socket();
        try {
            clientSocket.connect(address, TIMEOUT);
            if (!this.clientSocket.isConnected()) {
                log.error("Socket could not connect at port: " + address.getPort());
                throw new IOException("Socket could not connect");
            }
        } catch(ConnectException e) {
            log.error("could not connect at port " + address.getPort() + " : " + e.getLocalizedMessage());
        }
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        log.info("Client connected to server with address: " + address.toString());
        new Thread(this).start();
    }

    /**
     * Sends message to server.
     *
     * @param message Message to send.
     */
    public void sendMessage(String message) {
        if (message == null) {
            log.error("Client tried to send null message.");
        }
        out.println(message);
    }

    /**
     * Runs.
     */
    @Override
    public void run() {
        running = true;
        while (running) {
            try {
                message = in.readLine();
                if (message != null) {
                    messageHandler.handleMessage(message);
                } else {
                    terminate();
                }
            } catch (IOException e) {
                log.error("Could not read line from server ", e);
            }
        }
        terminate();
    }

    /**
     * Closes client.
     */
    public void terminate() {
        running = false;
        try {
            in.close();
            out.close();
            clientSocket.close();
            log.info("Client closed successfully");

        } catch (IOException e) {
            log.error("Failed to close client socket");
        }
    }
}
