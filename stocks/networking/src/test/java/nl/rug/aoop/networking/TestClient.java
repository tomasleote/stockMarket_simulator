package nl.rug.aoop.networking;

import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.networking.client.Client;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Client.
 */
@Slf4j
public class TestClient {
    private int serverPort;
    private boolean serverStarted = false;
    private PrintWriter serverOut;

    private BufferedReader serverIn;

    private void startServer(){
        new Thread(() -> {
            try {
                ServerSocket s = new ServerSocket(0);
                serverPort  = s.getLocalPort();
                serverStarted = true;

                Socket serverSocket = s.accept();
                serverOut = new PrintWriter(serverSocket.getOutputStream(), true);
                serverIn = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
                log.info("Server started");

            }catch (IOException e){
                log.error("Server did not work", e);
            }
        }).start();

        await().atMost(2, TimeUnit.SECONDS).until(()->serverStarted);
    }

    private Client startClient() throws IOException {
        startServer();

        InetSocketAddress address = new InetSocketAddress("localhost", serverPort);
        MessageHandler mockHandler = Mockito.mock(MessageHandler.class);
        Client client = new Client(address, mockHandler);
        return client;
    }

    @Test
    public void testReceiveMessage() throws IOException {
        Client client = startClient();

        new Thread(client).start();
        await().atMost(3, TimeUnit.SECONDS).until(client::isRunning);

        assertTrue(client.isRunning());

        String message = "Test 1";
        serverOut.println(message);
        serverOut.flush();
        await().atMost(2, TimeUnit.SECONDS).with()
                .pollInterval(10, TimeUnit.MILLISECONDS)
                .until(()->client.getMessage() != null);
        Mockito.verify(client.getMessageHandler()).handleMessage(message);
    }

    @Test
    public void testSendMessage() throws IOException{
        Client client = startClient();
        new Thread(client).start();
        await().atMost(3, TimeUnit.SECONDS).until(client::isRunning);

        String message = "test";
        client.sendMessage(message);
        String messageServer = serverIn.readLine();
        assertEquals(messageServer, message);
    }

    @Test
    public void testInvalidServerSocket() throws IOException {
        startServer();

        InetSocketAddress address = new InetSocketAddress("localhost", serverPort + 42);
        MessageHandler mockHandler = Mockito.mock(MessageHandler.class);
        assertThrows(
                IOException.class,
                () -> new Client(address, mockHandler),
                "hmm"
        );
    }
}

