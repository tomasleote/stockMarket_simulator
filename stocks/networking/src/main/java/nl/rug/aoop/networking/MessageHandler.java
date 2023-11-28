package nl.rug.aoop.networking;

/**
 * Interface for message handler.
 */
public interface MessageHandler {

    /**
     * Handles message.
     * @param message message to handle.
     */
    void handleMessage(String message);

}
