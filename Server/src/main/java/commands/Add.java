package commands;

import commands.exceptions.ExecuteQueryException;
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
        if (request.getArgs().length != argsNumber) throw new IllegalArgsNumberRequestException(String.valueOf(argsNumber));
        if (request.getOrganization() == null) throw new IllegalFieldWrapperRequestException("отсутствует передаваемый элемент");
        if (databaseManager.addOrganization(request.getOrganization(), request.getUser())) {
            sender.send(socketAddress, new Response("Элемент успешно добавлен.", Status.SUCCESS));
        } else {
            throw new ExecuteQueryException();
        }
    }
}