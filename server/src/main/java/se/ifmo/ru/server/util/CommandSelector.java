package se.ifmo.ru.server.util;

import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import se.ifmo.ru.common.dto.Request;
import se.ifmo.ru.common.dto.Response;
import se.ifmo.ru.common.dto.Status;
import se.ifmo.ru.common.model.exceptions.ElementConstructorException;
import se.ifmo.ru.server.commands.*;
import se.ifmo.ru.server.commands.exceptions.IllegalArgsNumberRequestException;
import se.ifmo.ru.server.database.DatabaseManager;
import se.ifmo.ru.server.util.exceptions.UniqueElementException;
import se.ifmo.ru.server.util.transceiving.Sender;

public class CommandSelector {
    private static final Logger logger = LogManager.getLogger();
    private final HashMap<String, Command> commands = new HashMap<>();
    private final Sender sender;

    public CommandSelector(Sender sender, DatabaseManager databaseManager) {
        this.sender = sender;
        addCommand(new Help(sender, databaseManager));
        addCommand(new Info(sender, databaseManager));
        addCommand(new Show(sender, databaseManager));
        addCommand(new Add(sender, databaseManager));
        addCommand(new Update(sender, databaseManager));
        addCommand(new RemoveById(sender, databaseManager));
        addCommand(new Clear(sender, databaseManager));
        addCommand(new ExecuteScript(sender, databaseManager));
        addCommand(new AddIfMax(sender, databaseManager));
        addCommand(new AddIfMin(sender, databaseManager));
        addCommand(new RemoveGreater(sender, databaseManager));
        addCommand(new SumOfAnnualTurnover(sender, databaseManager));
        addCommand(new PrintDescending(sender, databaseManager));
        addCommand(new PrintFieldAscendingEmployeesCount(sender, databaseManager));
        addCommand(new Register(sender, databaseManager));
        addCommand(new Login(sender, databaseManager));
    }

    public void addCommand(Command command) {
        commands.put(command.getName(), command);
    }

    public List<Command> getCommands() {
        return new ArrayList<>(commands.values());
    }

    /**
     * Исполнить запрос клиента
     *
     * @param request запрос от клиента {@link Request}
     */
    public void executeRequest(SocketAddress socketAddress, Request request) {
        new Thread(() -> {
            Request validatedRequest = validateRequest(socketAddress, request);
            if (validatedRequest != null) {
                commands.get(request.getCommand()).execute(socketAddress, request);
            }
        }).start();
    }

    private Request validateRequest(SocketAddress socketAddress, Request request) {
        if (request == null) {
            logger.warn("Невозможно распознать команду");
            sender.send(socketAddress, new Response("Невозможно распознать команду.", Status.ERROR));
        } else {
            if (!commands.containsKey(request.getCommand())) {
                logger.warn("Несуществующая команда в запросе");
                sender.send(socketAddress, new Response("Такой команды нет. Введите help, чтобы получить список и описание команд.", Status.ERROR));
            }
            try {
                return request;
            } catch (IllegalArgsNumberRequestException e) {
                logger.error("Request error", e);
                sender.send(socketAddress, new Response("Невереное число аргументов. Команда принимает " + e.getMessage() + " аргументов.", Status.ERROR));
            } catch (ElementConstructorException e) {
                logger.error("Request error", e);
                sender.send(socketAddress, new Response("Неверное поле " + e.getMessage() + "в переданном элементе.", Status.ERROR));
            } catch (IllegalArgumentException | UniqueElementException | NullPointerException e) {
                logger.error("Request error", e);
                sender.send(socketAddress, new Response(e.getMessage(), Status.ERROR));
            }
        }
        return null;
    }
}
