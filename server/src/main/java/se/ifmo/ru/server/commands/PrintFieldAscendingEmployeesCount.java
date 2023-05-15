package se.ifmo.ru.server.commands;

import java.net.SocketAddress;
import java.util.Comparator;
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
 * Класс команды PrintFieldAscendingEmployeesCount
 */
public class PrintFieldAscendingEmployeesCount extends Command {
    public PrintFieldAscendingEmployeesCount(Sender sender, DatabaseManager databaseManager) {
        this.name = "print_field_ascending_employees_count";
        this.help = "Вывести значения поля employeesCount всех элементов в порядке возрастания";
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
        List<Organization> list = databaseManager.getOrganizations();
        list.sort(Comparator.comparingInt(Organization::getEmployeesCount));
        sender.send(socketAddress, new Response(list.isEmpty() ? "Коллекция пуста" : null, list, Status.SUCCESS));
    }
}
