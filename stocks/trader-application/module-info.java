module trader-application {
    exports nl.rug.aoop.trades;
    exports nl.rug.aoop.trades.commands;
    exports nl.rug.aoop.trades.model;
    requires static lombok;
    requires org.slf4j;
    requires org.mockito;
    requires networking;
    requires message.queue;
    requires command;
    requires stock.market.ui;
    requires util;
    requires org.jetbrains.annotations;
    requires com.google.gson;
    requires com.fasterxml.jackson.databind;
    opens nl.rug.aoop.trades.commands to com.fasterxml.jackson.databind, com.google.gson;
    opens nl.rug.aoop.trades to com.fasterxml.jackson.databind, com.google.gson;
}