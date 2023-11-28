package nl.rug.aoop.networking;

/**
 * Interface for Communicators.
 */
public interface Communicator {
    /**
     * Sends a message.
     * @param message message to be sent.
     */
    void sendMessage(String message);

    /**
     * Terminates a process.
     */
    void terminate();
}
