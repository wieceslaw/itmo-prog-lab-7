package se.ifmo.ru.server.commands;

import java.net.SocketAddress;
import java.util.List;
import se.ifmo.ru.common.dto.Request;
import se.ifmo.ru.common.dto.Response;
import se.ifmo.ru.common.dto.Status;
import se.ifmo.ru.common.model.Organization;
import se.ifmo.ru.server.commands.exceptions.IllegalArgsNumberRequestException;
import se.ifmo.ru.server.commands.exceptions.IllegalFieldWrapperRequestException;
import se.ifmo.ru.server.database.DatabaseManager;
import se.ifmo.ru.server.util.transceiving.Sender;

/**
 * Класс команды RemoveGrater
 */
public class RemoveGreater extends Command {
    public RemoveGreater(Sender sender, DatabaseManager databaseManager) {
        this.name = "remove_greater";
        this.help = "Удалить из коллекции все элементы, превышающие заданный";
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
        List<Organization> list = databaseManager.getOrganizations();
        if (list.isEmpty()) sender.send(socketAddress, new Response("Коллекция пуста.", Status.SUCCESS));
        int count = list.size();
        Organization organization = request.getOrganization();
        list.stream()
                .filter(s -> (s.compareTo(organization) > 0))
                .forEach(o -> databaseManager.removeOrganization(o, request.getUser()));
        if (count == databaseManager.getOrganizations().size()) {
            sender.send(socketAddress, new Response("Элементов больше данного не было найдено.", Status.SUCCESS));
        } else {
            sender.send(socketAddress, new Response("Удалено элементов " + (count - databaseManager.getOrganizations().size()), Status.SUCCESS));
        }
    }
}
