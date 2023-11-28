<br />
<p align="center">
  <h1 align="center">Stock Market Simulation</h1>

  <p align="center">
    Stock Application shakes hand with Trader Application
  </p>
</p>

## Table of Contents

* [About the Project](#about-the-project)
  * [Built With](#built-with)
* [Getting Started](#getting-started)
  * [Prerequisites](#prerequisites)
  * [Installation](#installation)
  * [Running](#running)
* [Modules](#modules)
* [Evaluation](#evaluation)
* [Key Features](#key-Features)
* [Extensibility](#extensibility)

## About The Project

This project is an simulation of a stock market environment, where automated trading bots engage in buying and selling stocks. It showcases a real-time, dynamic trading platform that combines various aspects of stock market operations, including different order types, real-time price updates, and trader portfolio management Traders communicate with the Stock Market throught the networking module, sending messages to a PriorityBlockingQueue, which are then received one by one by the Stock Market. The project began simple, with a Message class and some queues, then we introduced the networking module and using that, we created a Stock Market simulation that works over the network. The application also includes an UI that nicely displays the Stock Exchange happening in real time. 

## Getting Started

To get a local copy up and running follow these simple steps.

### Prerequisites

* [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) or higher
* [Maven 3.6](https://maven.apache.org/download.cgi) or higher

### Installation

1. Navigate to the `stockRegistry` directory
2. Clean and build the project using:
```sh
mvn install
```

### Running
To run the program, this is what you have to do: 
1. Run StockMain by right-clicking and pressing run;
2. Run TraderMain, similarly.

The StockMain creates a server which keeps track of stocks and of traders' information, and the TraderMain connects to it as a client and begins to send orders.
## Modules

1. command module
   - this module contains the command interface, which is the parent of all commands in the project
   - it also contains the commandHandler, which is responsible for matching and executing commands.
   - to easily create the commandHandler, the module also contains the commandHandlerFactory interface
   - the module is tested.
2. message-queue module
   - MqPut command, responsible for adding messages to the queue;
   - a factory that creates a command handler for a queue;
   - different types of queues (ordered, unordered, thread safe);
   - users which are the network producer, responsible for sending messages to the server, publisher, responsible for enqueueing messages, and subscriber, responsible for dequeueing messages.
   - this module is responsible for all operations involving queues and is tested.
3. networking module
   - this module is responsible for receiving and sending operations over the network.
   - it is easily extendable because it uses the client-server pattern.
   - it contains a client which can connect to a server via a client handler, and a server.
4. stock-application module
   - this module is responsible for everything stock related, including updaters for traders, the trader model and trader registry.
   - the order package contains the buy and sell orders, the more general limit order and the resolved order.
   - the command package contains the corresponding commands for buy and sell orders, the commands to update stocks and trader and a new Stock command handler factory which creates a command handler including these commands.
   - the module package contains the representations of traders and stocks: a trader registry has traders, and each trader has a portfolio of owned stocks, and the stock registry has stocks. The stock exchange class combines these two registries.
   - the stock application package contains the classes which tie everything together. The trader updater is responsible for keeping the client traders up to date with their own data, while the market updater is responsible for updating the market. The stock manager is the one who loads from the yaml files, reads messages and gives orders accordingly. Finally, the stock main, which is the main class of the stock application, and it starts a server with everything initiated.
5. stock-market-ui
   - this module is responsible for the UI.
6. trader-application
   - this module is responsible for creating bot clients (TraderBot) that connect to the stock application server and create orders using a given trade strategy, in this case it is randomized inside the random trade strategy class.
   - the command module contains the commands which are used to receive the updates sent by the server and update traders and stocks accordingly; it also includes a trader command handler factory that creates a handler with these commands.
   - the trader manager is the one who reads messages from the queue and gives orders accordingly, while also being responsible for loading stocks and traders from the yaml files and logging the traders. This manager also starts the bots.
   - the trader main initiates everything and attempts to connect to the stock application.

## Design


The first important pattern included in the project is the command pattern, which is used to operate on queues. Using this pattern completely decouples the queue from the other operations. Additionally,  because the parameter of the execute function inside each command is a map, we can greatly customize the input while keeping functionality. The extendability of this pattern is proven by the fact that we were able to add so many commands after finishing the implementation of the command interface and the command handler.
The second pattern which is useful is the factory pattern, which is used to create the command handlers with their needed commands in the respective modules. This pattern, together with the command pattern make for a very extendable way of having commands in the project.
The third pattern used is the client server pattern, which was used to connect the stock application and the trader application. The networking module is straightforward and decoupled from all the other modules, and the stock app and trader app make use of the pattern to connect to one another without too much trouble.

## Evaluation

Our implementation is mostly stable. The stock app and trader app both work as intended. One potential bug is that sometimes, in the trader main logs you can find chunks of errors saying that a stock cannot be removed because the trader does not have it. This may be caused by a faulty update stocks method call, or the trader app sometimes does not receive the updated portfolio of that certain trader. A good way to handle this would be to handle the "does not own this stock" earlier in the call hierarchy and not at the very bottom level, at portfolio.

Every class besides the main classes have automatic unit tests which cover most edge cases, while the main classes were tested manually, through trial and error.
We made use of Mockito to create mocks of classes where needed to make testing easier.

## Key Features
- **Modular Architecture**: The project is structured into distinct modules such as the stock exchange simulator, trader management, and networking, ensuring a clear separation of concerns and ease of maintenance.
- **Automated Trading Bots**: Simulates the actions of multiple traders using bots, each with unique strategies, reacting to market changes in real-time.
- **Support for Various Order Types**: Efficiently handles different types of orders, including limit and market orders. The system is designed to be extensible, allowing for easy addition of new order types.
- **Real-Time Market Updates**: Updates stock prices and trader portfolio values dynamically, reflecting the immediate impact of market transactions.
- **Decoupled Networking**: Robust communication system that is decoupled from core business logic, facilitating scalability and network interactions.
- **Extensibility for New Features**: Designed for easy integration of new functionalities like additional order types or trading strategies.
- **Customizable Market Environment**: Traders and stocks can be added or modified easily through data files, allowing for a customizable and diverse market simulation.

### Extensibility
- **Adding New Order Types**: New order types can be incorporated by extending the `Order` interface and integrating them into the existing order processing flow.
- **Customizing Bots and Stocks**: The system allows for the addition of new bots and stocks by updating the respective YAML data files.
- Feel free to collaborate and extend on this project, the purpose of this is simply educational purposes, and was an assigment for one of my courses.


