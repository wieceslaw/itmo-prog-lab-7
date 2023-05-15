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
 * Класс команды SumOfAnnualTurnover
 */
public class SumOfAnnualTurnover extends Command {
    public SumOfAnnualTurnover(Sender sender, DatabaseManager databaseManager) {
        this.name = "sum_of_annual_turnover";
        this.help = "Вывести сумму значений поля annualTurnover для всех элементов коллекции";
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
        int sum = databaseManager.getOrganizations().stream()
                .map(Organization::getAnnualTurnover)
                .reduce(0, Integer::sum);
        sender.send(socketAddress, new Response(String.valueOf(sum), Status.SUCCESS));
    }
}
