package nl.rug.aoop.stocks.commands;

import nl.rug.aoop.command.Command;
import nl.rug.aoop.stocks.orders.BuyOrder;
import nl.rug.aoop.stocks.orders.OrderHandler;

import java.util.Map;

/**
 * Class BuyOrderCommand that implements Command Interface.
 */
public class BuyOrderCommand implements Command {

    private OrderHandler orderHandler;

    /**
     * Constructor.
     * @param handler the handler to execute on.
     */
    public BuyOrderCommand(OrderHandler handler) {
        this.orderHandler = handler;
    }

    /**
     * execute method.
     * @param options map containing "body" and the order to be handled as a JsonString.
     */
    @Override
    public void execute(Map<String, Object> options) {
        String orderJson = (String) options.get("body");
        BuyOrder order = BuyOrder.fromJson(orderJson); 
        orderHandler.handleOrder(order);
    }
}
