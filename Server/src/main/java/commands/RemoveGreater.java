package commands;

import commands.exceptions.IllegalArgsNumberRequestException;
import commands.exceptions.IllegalFieldWrapperRequestException;
import database.DatabaseManager;
import dto.Status;
import model.Organization;
import dto.Request;
import dto.Response;
import util.CollectionManager;
import util.transceiving.Sender;

import java.net.SocketAddress;
import java.util.List;

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
        if (request.getArgs().length != argsNumber) throw new IllegalArgsNumberRequestException(String.valueOf(argsNumber));
        if (request.getOrganization() == null) throw new IllegalFieldWrapperRequestException("отсутствует передаваемый элемент");
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