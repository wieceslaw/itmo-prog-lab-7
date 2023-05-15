package se.ifmo.ru.server.commands;

import java.net.SocketAddress;
import se.ifmo.ru.common.dto.Request;
import se.ifmo.ru.common.dto.Response;
import se.ifmo.ru.common.dto.Status;
import se.ifmo.ru.common.model.Organization;
import se.ifmo.ru.server.commands.exceptions.IllegalArgsNumberRequestException;
import se.ifmo.ru.server.commands.exceptions.IllegalFieldWrapperRequestException;
import se.ifmo.ru.server.database.DatabaseManager;
import se.ifmo.ru.server.util.transceiving.Sender;

/**
 * Класс команды Update
 */
public class Update extends Command {
    public Update(Sender sender, DatabaseManager databaseManager) {
        this.name = "update";
        this.help = "Обновить значение элемента коллекции, id которого равен заданному";
        this.argsNumber = 1;
        this.sender = sender;
        this.databaseManager = databaseManager;
    }

    @Override
    public void execute(SocketAddress socketAddress, Request request) {
        if (request.getArgs().length != argsNumber)
            throw new IllegalArgsNumberRequestException(String.valueOf(argsNumber));
        if (request.getOrganization() == null)
            throw new IllegalFieldWrapperRequestException("отсутствует передаваемый элемент");
        Long id = Long.parseLong(request.getArgs()[0]);
        Organization organization = databaseManager.getOrganization(id);
        if (organization == null) throw new NullPointerException("Элемента с таким ID не найдено.");
        databaseManager.updateOrganization(request.getOrganization(), request.getUser(), id);
        sender.send(socketAddress, new Response("Элемент обновлен.", Status.SUCCESS));
    }
}
