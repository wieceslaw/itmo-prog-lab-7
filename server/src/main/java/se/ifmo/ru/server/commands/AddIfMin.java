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
 * Класс команды AddIfMin
 */
public class AddIfMin extends Command {
    public AddIfMin(Sender sender, DatabaseManager databaseManager) {
        this.name = "add_if_min";
        this.help = "Добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции";
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
        Organization elem = request.getOrganization();
        Organization min = databaseManager.getMinOrganization();
        if (min == null || elem.compareTo(min) < 0) {
            databaseManager.addOrganization(request.getOrganization(), request.getUser());
            sender.send(socketAddress, new Response("Элемент добавлен", Status.SUCCESS));
        } else {
            sender.send(socketAddress, new Response("Элемент не был добавлен.", Status.SUCCESS));
        }
    }
}
