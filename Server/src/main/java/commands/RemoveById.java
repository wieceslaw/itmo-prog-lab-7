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

/**
 * Класс команды RemoveById
 */
public class RemoveById extends Command {
    public RemoveById(Sender sender, DatabaseManager databaseManager) {
        this.name = "remove_by_id";
        this.help = "Удалить элемент из коллекции по его id";
        this.argsNumber = 1;
        this.sender = sender;
        this.databaseManager = databaseManager;
    }

    @Override
    public void execute(SocketAddress socketAddress, Request request) {
        if (request.getArgs().length != argsNumber) throw new IllegalArgsNumberRequestException(String.valueOf(argsNumber));
        if (request.getOrganization() != null) throw new IllegalFieldWrapperRequestException("команда не принимает элемент");
        long id = Long.parseLong(request.getArgs()[0]);
        Organization organization = databaseManager.getOrganization(id);
        if (organization == null) throw new NullPointerException("Элемент с таким ID не найден.");
        databaseManager.removeOrganization(organization, request.getUser());
        sender.send(socketAddress, new Response("Элемент удален.", Status.SUCCESS));
    }
}