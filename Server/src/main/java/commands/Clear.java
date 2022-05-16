package commands;

import commands.exceptions.IllegalArgsNumberRequestException;
import commands.exceptions.IllegalFieldWrapperRequestException;
import database.DatabaseManager;
import dto.Request;
import dto.Response;
import dto.Status;
import util.CollectionManager;
import util.transceiving.Sender;

import java.net.SocketAddress;

/**
 * Класс команды Clear
 */
public class Clear extends Command {
    public Clear(Sender sender, DatabaseManager databaseManager) {
        this.name = "clear";
        this.help = "Очистить коллекцию";
        this.argsNumber = 0;
        this.sender = sender;
        this.databaseManager = databaseManager;
    }

    @Override
    public void execute(SocketAddress socketAddress, Request request) {
        if (request.getArgs().length != argsNumber) throw new IllegalArgsNumberRequestException(String.valueOf(argsNumber));
        if (request.getOrganization() != null) throw new IllegalFieldWrapperRequestException("команда не принимает элемент");
        databaseManager.clearOrganizations(request.getUser());
        sender.send(socketAddress, new Response("Удалены организации, созданные пользователем.", Status.SUCCESS));
    }
}
