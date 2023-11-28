package nl.rug.aoop.command;

import java.util.Map;

/**
 * Command interface.
 */
public interface Command {
    /**
     * method to execute the command.
     * @param options map containing the command id(string) and its parameter.
     */
    void execute(Map<String, Object> options);

}
