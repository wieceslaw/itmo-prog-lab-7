package se.ifmo.ru.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import se.ifmo.ru.common.dto.Request;
import se.ifmo.ru.common.util.Convertor;
import se.ifmo.ru.server.database.DatabaseManager;
import se.ifmo.ru.server.util.CommandSelector;
import se.ifmo.ru.server.util.ServerCLI;
import se.ifmo.ru.server.util.transceiving.Receiver;
import se.ifmo.ru.server.util.transceiving.Sender;

public class Server {
    private DatagramSocket socket;
    private final Receiver receiver;
    private final Sender sender;
    private DatabaseManager databaseManager;
    private CommandSelector commandSelector;
    private DatagramPacket datagramPacket;
    private SocketAddress socketAddress;
    private Request request;
    private ServerCLI serverCLI;
    private static final Logger logger = LogManager.getLogger();

    public Server() {
        logger.info("Начало инициализации сервера...");
        try {
            socket = new DatagramSocket(2001);
        } catch (IOException e) {
            logger.error("Ошибка при открытии сокета.", e);
            System.exit(-1);
        }
        this.receiver = new Receiver(socket);
        this.sender = new Sender(socket);
        this.databaseManager = new DatabaseManager();
        this.commandSelector = new CommandSelector(sender, databaseManager);
        this.serverCLI = new ServerCLI();
        logger.info("Конец инициализации сервера.");
    }

    public void run() {
        new Thread(serverCLI).start();
        logger.info("Сервер запущен и готов принимать запросы.");
        while (true) {
            datagramPacket = receiver.receive();
            socketAddress = datagramPacket.getSocketAddress();
            try {
                request = (Request) Convertor.convertBytesToObject(datagramPacket.getData());
            } catch (IOException | ClassNotFoundException e) {
                logger.error("Ошибка сериализации");
            }
            if (request != null) {
                logger.info("Запрос клиента=" + request);
                commandSelector.executeRequest(socketAddress, request);
            }
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.run();
    }
}
