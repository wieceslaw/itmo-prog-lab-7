package commands;

import database.DatabaseManager;
import dto.Request;
import util.transceiving.Sender;

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