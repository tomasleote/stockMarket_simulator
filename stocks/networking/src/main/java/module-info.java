module networking {
    // If you are using Mockito in another module to mock a networking item from this package,
    // then add "opens .. to ..". If we are mocking e.g. a NetworkClient interface
    // in the module messagequeue, then we need:
    //    opens nl.rug.aoop.networking to messagequeue;
    // Again, sub-packages have to be explicitly opened as well. Any error messages should indicate this.
    // If you want to allow this module to be used in other modules, uncomment the following line:
    //    exports nl.rug.aoop.networking;
    // Note that this will not include any sub-level packages. If you want to export more, then add those as well:
    exports nl.rug.aoop.networking.server;
    exports nl.rug.aoop.networking.client;
    exports nl.rug.aoop.networking;
    requires static lombok;
    requires org.slf4j;
    requires org.mockito;
}