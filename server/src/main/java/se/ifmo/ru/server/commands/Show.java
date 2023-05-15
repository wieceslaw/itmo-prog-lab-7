package se.ifmo.ru.server.commands;

import java.net.SocketAddress;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import se.ifmo.ru.common.dto.Request;
import se.ifmo.ru.common.dto.Response;
import se.ifmo.ru.common.dto.Status;
import se.ifmo.ru.common.model.Organization;
import se.ifmo.ru.server.commands.exceptions.IllegalArgsNumberRequestException;
import se.ifmo.ru.server.commands.exceptions.IllegalFieldWrapperRequestException;
import se.ifmo.ru.server.database.DatabaseManager;
import se.ifmo.ru.server.util.transceiving.Sender;

/**
 * Класс команды Show
 */
public class Show extends Command {
    public Show(Sender sender, DatabaseManager databaseManager) {
        this.name = "show";
        this.help = "Вывести в стандартный поток вывода все элементы коллекции в строковом представлении";
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
        List<Organization> sortedByNameList = databaseManager.getOrganizations().stream().sorted(Comparator.comparing(Organization::getName)).collect(Collectors.toList());
        sender.send(socketAddress, new Response(databaseManager.getOrganizations().isEmpty() ? "Коллекция пуста." : null, sortedByNameList, Status.SUCCESS));
    }
}
