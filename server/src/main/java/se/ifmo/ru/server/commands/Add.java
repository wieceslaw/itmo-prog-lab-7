package se.ifmo.ru.server.commands;

import java.net.SocketAddress;
import se.ifmo.ru.common.dto.Request;
import se.ifmo.ru.common.dto.Response;
import se.ifmo.ru.common.dto.Status;
import se.ifmo.ru.server.commands.exceptions.ExecuteQueryException;
import se.ifmo.ru.server.commands.exceptions.IllegalArgsNumberRequestException;
import se.ifmo.ru.server.commands.exceptions.IllegalFieldWrapperRequestException;
import se.ifmo.ru.server.database.DatabaseManager;
import se.ifmo.ru.server.util.transceiving.Sender;

/**
 * Класс команды Add
 */
public class Add extends Command {
    public Add(Sender sender, DatabaseManager databaseManager) {
        this.name = "add";
        this.help = "Добавить новый элемент в коллекцию";
        this.argsNumber = 0;
        this.sender = sender;
        this.databaseManager = databaseManager;
    }

    @Override
    public void execute(SocketAddress socketAddress, Request request) {
        if (request.getArgs().length != argsNumber)
            throw new IllegalArgsNumberRequestException(String.valueOf(argsNumber));
        if (request.getOrganization() == null)
            throw new IllegalFieldWrapperRequestException("отсутствует передаваемый элемент");
        if (databaseManager.addOrganization(request.getOrganization(), request.getUser())) {
            sender.send(socketAddress, new Response("Элемент успешно добавлен.", Status.SUCCESS));
        } else {
            throw new ExecuteQueryException();
        }
    }
}
