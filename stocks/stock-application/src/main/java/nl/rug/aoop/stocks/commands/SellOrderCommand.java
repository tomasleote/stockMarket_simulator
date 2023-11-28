package nl.rug.aoop.stocks.commands;

import nl.rug.aoop.command.Command;
import nl.rug.aoop.stocks.orders.OrderHandler;
import nl.rug.aoop.stocks.orders.SellOrder;

import java.util.Map;

/**
 * Class SellOrderCommand that implements Command interface.
 */
public class SellOrderCommand implements Command {

    private OrderHandler handler;

    /**
     * Constructor.
     * @param handler the handler to execute on.
     */
    public SellOrderCommand(OrderHandler handler) {
        this.handler =  handler;
    }

    /**
     * execute method.
     * @param options map containing "body" and the order to be handled as a JsonString.
     */
    @Override
    public void execute(Map<String, Object> options) {
        String orderJson = (String) options.get("body");
        SellOrder sellOrder = SellOrder.fromJson(orderJson);
        handler.handleOrder(sellOrder);
    }
}
