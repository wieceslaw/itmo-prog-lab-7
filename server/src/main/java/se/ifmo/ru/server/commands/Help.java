package se.ifmo.ru.server.commands;

import java.net.SocketAddress;
import se.ifmo.ru.common.dto.Request;
import se.ifmo.ru.common.dto.Response;
import se.ifmo.ru.common.dto.Status;
import se.ifmo.ru.server.commands.exceptions.IllegalArgsNumberRequestException;
import se.ifmo.ru.server.commands.exceptions.IllegalFieldWrapperRequestException;
import se.ifmo.ru.server.database.DatabaseManager;
import se.ifmo.ru.server.util.CommandSelector;
import se.ifmo.ru.server.util.transceiving.Sender;

/**
 * Класс команды Help
 */
public class Help extends Command {
    public Help(Sender sender, DatabaseManager databaseManager) {
        this.name = "help";
        this.help = "Вывести справку по доступным командам";
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
        CommandSelector commandSelector = new CommandSelector(sender, databaseManager);
        StringBuilder helpDesc = new StringBuilder();
        for (Command command : commandSelector.getCommands()) {
            if (command.help != null) helpDesc.append(command.name).append(": ").append(command.help).append('\n');
        }
        helpDesc.deleteCharAt(helpDesc.length() - 1);
        sender.send(socketAddress, new Response(helpDesc.toString(), Status.SUCCESS));
    }
}
