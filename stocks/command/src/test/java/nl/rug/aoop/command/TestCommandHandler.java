package nl.rug.aoop.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class TestCommandHandler {
    private CommandHandler testCommandHandler;
    private Map<String, Command> testCommandMap;
    private Command testCommand;

    @BeforeEach
    void setUp() {
        testCommand = Mockito.mock(Command.class);
        testCommandMap = new HashMap<>();
        testCommandMap.put("TestCommand", testCommand);
        testCommandHandler = new CommandHandler(testCommandMap);
    }

    @Test
    void setTestCommandHandlerConstructor() {
        CommandHandler emptyConst = new CommandHandler();
        assertNotNull(emptyConst);
    }

    @Test
    void testExecuteCommand() {
        Command mockCommand = Mockito.mock(Command.class);
        String key = "Test";
        testCommandHandler.registerCommand(key, mockCommand);
        Map empty = new HashMap<>();
        testCommandHandler.executeCommand(key, empty);
        Mockito.verify(mockCommand).execute(empty);
    }

    @Test
    void testExecuteNullCommand() {
        assertDoesNotThrow(() -> testCommandHandler.executeCommand(null, null));
    }

    @Test
    void testGetCommandMap() {
        assertEquals(testCommandHandler.getCommandMap(), testCommandMap);
    }

    @Test
    void testRegisterCommand() {
        Command newCommand = Mockito.mock(Command.class);
        testCommandHandler.registerCommand("NewCommand", newCommand);
        assertEquals(testCommandHandler.getCommandMap(), Map.of("TestCommand", testCommand, "NewCommand", newCommand));
    }

    @Test
    void testRegisterNullCommand() {
        assertDoesNotThrow(() -> testCommandHandler.registerCommand(null, null));
    }
}
