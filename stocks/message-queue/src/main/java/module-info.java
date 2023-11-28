module message.queue {
    exports nl.rug.aoop.messagequeue.queues;
    exports nl.rug.aoop.messagequeue;
    exports nl.rug.aoop.messagequeue.factory;
    exports nl.rug.aoop.messagequeue.users;
    exports nl.rug.aoop.messagequeue.commands;
    requires static lombok;
    requires org.slf4j;
    requires org.mockito;
    requires com.google.gson;
    requires networking;
    requires command;
}