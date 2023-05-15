package se.ifmo.ru.server.commands;

import java.net.SocketAddress;
import java.util.Collections;
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
 * Класс команды PrintDescending
 */
public class PrintDescending extends Command {
    public PrintDescending(Sender sender, DatabaseManager databaseManager) {
        this.name = "print_descending";
        this.help = "Вывести элементы коллекции в порядке убывания";
        this.argsNumber = 0;
        this.sender = sender;
        this.databaseManager = databaseManager;
    }

    @Override
    public void execute(SocketAddress socketAddress, Request request) {
        if (request.getArgs().length != argsNumber)
            throw new IllegalArgsNumberRequestException(String.valueOf(argsNumber));
        if (request.getOrganization() != null)
            throw new IllegalFieldWrapperRequestException("команда не принимает элемент");
        List<Organization> list = databaseManager.getSortedOrganizations();
        Collections.reverse(list);
        sender.send(socketAddress, new Response(list.isEmpty() ? "Коллекция пуста." : null, list, Status.SUCCESS));
    }
}
