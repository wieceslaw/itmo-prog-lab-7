package se.ifmo.ru.server.commands;

import java.net.SocketAddress;
import java.text.SimpleDateFormat;
import se.ifmo.ru.common.dto.Request;
import se.ifmo.ru.common.dto.Response;
import se.ifmo.ru.common.dto.Status;
import se.ifmo.ru.server.commands.exceptions.IllegalArgsNumberRequestException;
import se.ifmo.ru.server.commands.exceptions.IllegalFieldWrapperRequestException;
import se.ifmo.ru.server.database.DatabaseManager;
import se.ifmo.ru.server.util.transceiving.Sender;

/**
 * Класс команды Info
 */
public class Info extends Command {
    public Info(Sender sender, DatabaseManager databaseManager) {
        this.name = "info";
        this.help = "Вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)";
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
        String pattern = "dd.M.yyyy hh:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String str = "Дата инициализации: " + simpleDateFormat.format(databaseManager.getCreationDate()) + '\n' +
                "Количество элементов: " + databaseManager.getOrganizations().size();
        sender.send(socketAddress, new Response(str, Status.SUCCESS));
    }
}
