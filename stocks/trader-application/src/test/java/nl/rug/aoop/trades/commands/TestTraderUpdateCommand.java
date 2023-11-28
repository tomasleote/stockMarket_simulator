package nl.rug.aoop.trades.commands;

import nl.rug.aoop.command.Command;
import nl.rug.aoop.stocks.model.Portfolio;
import nl.rug.aoop.stocks.model.Trader;
import nl.rug.aoop.trades.TraderManager;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class TestTraderUpdateCommand {

    private TraderManager traderManager;
    private Command traderUpdateCommand;

    @Before
    public void setUp() {
        traderManager = mock(TraderManager.class);
        traderUpdateCommand = new TraderUpdateCommand(traderManager);
    }

    @Test
    public void testExecute() {
        // Create a sample trader JSON representation
        String traderJson = new Trader("id", "name", 100.0, new Portfolio(), 0).toJson();

        // Prepare the options map
        Map<String, Object> options = new HashMap<>();
        options.put("body", traderJson);

        // Execute the command
        traderUpdateCommand.execute(options);

        // Use ArgumentCaptor to capture the argument passed to updateTraders method
        ArgumentCaptor<Trader> traderCaptor = ArgumentCaptor.forClass(Trader.class);
        verify(traderManager).updateTraders(traderCaptor.capture());

        // Verify that the captured Trader object has the correct content
        Trader capturedTrader = traderCaptor.getValue();
        Trader expectedTrader = Trader.fromJson(traderJson);

        // Assert that the capturedTrader is equivalent to the expectedTrader
        assertEquals(expectedTrader.getId(), capturedTrader.getId());
        assertEquals(expectedTrader.getName(), capturedTrader.getName());
        assertEquals(expectedTrader.getFunds(), capturedTrader.getFunds(), 0.01);
        assertEquals(expectedTrader.getClientId(), expectedTrader.getClientId());
    }
}
