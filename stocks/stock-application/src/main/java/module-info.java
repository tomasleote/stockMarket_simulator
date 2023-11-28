module stock {
    exports nl.rug.aoop.stocks.stock.application;
    exports nl.rug.aoop.stocks.orders;
    exports nl.rug.aoop.stocks.model;
    requires static lombok;
    requires org.slf4j;
    requires org.mockito;
    requires networking;
    requires message.queue;
    requires command;
    requires stock.market.ui;
    requires util;
    requires org.jetbrains.annotations;
    requires com.fasterxml.jackson.databind;
    requires com.google.gson;
    opens nl.rug.aoop.stocks.model to com.fasterxml.jackson.databind, com.google.gson;
    opens nl.rug.aoop.stocks.stock.application to com.google.gson;
    opens nl.rug.aoop.stocks.orders to com.google.gson;
}