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
 * Класс команды AddIfMax
 */
public class AddIfMax extends Command {
    public AddIfMax(Sender sender, DatabaseManager databaseManager) {
        this.name = "add_if_max";
        this.help = "Добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции";
        this.argsNumber = 0;
        this.sender = sender;
        this.databaseManager = databaseManager;
    }

    @Override
    public void execute(SocketAddress socketAddress, Request request) {
        if (request.getArgs().length != argsNumber) throw new IllegalArgsNumberRequestException(String.valueOf(argsNumber));
        if (request.getOrganization() == null) throw new IllegalFieldWrapperRequestException("отсутствует передаваемый элемент");
        Organization elem = request.getOrganization();
        Organization max = databaseManager.getMaxOrganization();
        if (max == null || elem.compareTo(max) > 0) {
            databaseManager.addOrganization(elem, request.getUser());
            sender.send(socketAddress, new Response("Элемент добавлен", Status.SUCCESS));
        } else {
            sender.send(socketAddress, new Response("Элемент не был добавлен.", Status.SUCCESS));
        }
    }
}
