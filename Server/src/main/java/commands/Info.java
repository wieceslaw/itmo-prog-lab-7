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
import java.text.SimpleDateFormat;

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
        if (request.getArgs().length != argsNumber) throw new IllegalArgsNumberRequestException(String.valueOf(argsNumber));
        if (request.getOrganization() != null) throw new IllegalFieldWrapperRequestException("команда не принимает элемент");
        String pattern = "dd.M.yyyy hh:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String str = "Дата инициализации: " + simpleDateFormat.format(databaseManager.getCreationDate()) + '\n' +
                "Количество элементов: " + databaseManager.getOrganizations().size();
        sender.send(socketAddress, new Response(str, Status.SUCCESS));
    }
}