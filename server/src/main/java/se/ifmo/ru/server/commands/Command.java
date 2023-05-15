package se.ifmo.ru.server.commands;

import se.ifmo.ru.server.database.DatabaseManager;
import se.ifmo.ru.server.util.transceiving.Sender;
import se.ifmo.ru.common.dto.Request;

import java.net.SocketAddress;

/**
 * Класс базовой команды
 *
 */
public abstract class Command {
    protected String name;
    protected String help;
    protected int argsNumber;
    protected Sender sender;
    protected DatabaseManager databaseManager;

    public String getName() {
        return name;
    }
    public abstract void execute(SocketAddress socketAddress, Request request);
}
