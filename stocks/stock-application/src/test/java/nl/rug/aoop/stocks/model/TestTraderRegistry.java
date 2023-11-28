package nl.rug.aoop.stocks.model;

import nl.rug.aoop.stocks.model.Trader;
import nl.rug.aoop.stocks.model.TraderRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestTraderRegistry {
    private TraderRegistry traderRegistry;

    @BeforeEach
    void setUp() {
        traderRegistry = new TraderRegistry();
    }

//    @Test
//    void testAddTrader() {
//        Trader trader = new Trader("1", "John Doe", 10000, new Portfolio());
//        traderRegistry.addTrader(trader);
//        assertEquals(1, traderRegistry.getNumTraders());
//        assertTrue(traderRegistry.getTraders().contains(trader));
//    }

//    @Test
//    void testGetTraderById() {
//        Trader trader = new Trader("1", "John Doe", 10000, new Portfolio());
//        traderRegistry.addTrader(trader);
//        assertEquals(trader, traderRegistry.getTraderById("1"));
//    }

    @Test
    void testGetTraderByIdNonExistent() {
        assertNull(traderRegistry.getTraderById("nonexistent"));
    }

//    @Test
//    void testRemoveTrader() {
//        Trader trader = new Trader("1", "John Doe", 10000, new Portfolio());
//        traderRegistry.addTrader(trader);
//        traderRegistry.removeTrader("1");
//        assertEquals(0, traderRegistry.getNumTraders());
//        assertFalse(traderRegistry.getTraders().contains(trader));
//    }

//    @Test
//    void testRemoveTraderNonExistent() {
//        Trader trader = new Trader("1", "John Doe", 10000, new Portfolio());
//        traderRegistry.addTrader(trader);
//        traderRegistry.removeTrader("nonexistent");
//        assertEquals(1, traderRegistry.getNumTraders());
//        assertTrue(traderRegistry.getTraders().contains(trader));
//    }
}
