package nl.rug.aoop.command;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * CommandHandler class.
 */
@Slf4j
public class CommandHandler {
    private Map<String, Command> commandMap;

    /**
     * Constructor for the commandHandler class without a given map of commands.
     */
    public CommandHandler() {
        this.commandMap = new HashMap<>();
    }

    /**
     * Constructor for the commandHandler class with a given map of commands.
     * @param constructorMap the map of commands.
     */
    public CommandHandler(Map<String, Command> constructorMap) {
        this.commandMap = constructorMap;
    }

    /**
     * Method to register a command in the CommandHandler.
     * @param command the command string.
     * @param commandClass the command to be registered.
     */
    public void registerCommand(String command, Command commandClass) {
        try {
            commandMap.put(command, commandClass);
            log.info("Command " + command + " registered");
        } catch (NullPointerException e) {
            log.error("Couldn't register command", e);
        }
    }

    /**
     * Method to execute a given command.
     * @param command the command string to be executed.
     * @param options the message to be executed in Json format.
     */
    public void executeCommand(String command, Map<String, Object> options) {
        if(commandMap.containsKey(command)) {
            Command command1 = commandMap.get(command);
            command1.execute(options);
            log.info("Executed " + command);
        } else {
            log.warn("Client asked for invalid commandStr " + command);
        }
    }

    /**
     * Method to get the command map of the commandHandler.
     * @return the command map.
     */
    public Map<String, Command> getCommandMap() {
        return this.commandMap;
    }
}