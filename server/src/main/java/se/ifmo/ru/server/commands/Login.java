package se.ifmo.ru.server.commands;

import java.net.SocketAddress;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import se.ifmo.ru.common.dto.Request;
import se.ifmo.ru.common.dto.Response;
import se.ifmo.ru.common.dto.Status;
import se.ifmo.ru.common.model.User;
import se.ifmo.ru.server.commands.exceptions.IllegalArgsNumberRequestException;
import se.ifmo.ru.server.commands.exceptions.IllegalFieldWrapperRequestException;
import se.ifmo.ru.server.database.DatabaseManager;
import se.ifmo.ru.server.util.crypto.Encryptor;
import se.ifmo.ru.server.util.transceiving.Sender;

public class Login extends Command {
    private final Encryptor encryptor;
    private static final Logger logger = LogManager.getLogger();

    public Login(Sender sender, DatabaseManager databaseManager) {
        this.name = "login";
        this.sender = sender;
        this.argsNumber = 0;
        this.databaseManager = databaseManager;
        this.encryptor = new Encryptor();
    }

    @Override
    public void execute(SocketAddress socketAddress, Request request) {
        if (request.getArgs().length != argsNumber)
            throw new IllegalArgsNumberRequestException(String.valueOf(argsNumber));
        if (request.getOrganization() != null)
            throw new IllegalFieldWrapperRequestException("команда не принимает элемент");
        if (request.getUser() != null) {
            User hashedUser = new User(request.getUser().getLogin(), encryptor.generateHashForPassword(request.getUser().getPassword()));
            if (databaseManager.userExists(hashedUser)) {
                logger.info("Успешная авторизация пользователя=" + request.getUser());
                sender.send(socketAddress, new Response("Успешная авторизация.", Status.SUCCESS));
            } else {
                logger.warn("Ошибка авторизации пользователя=" + request.getUser());
                sender.send(socketAddress, new Response("Неверный логин или пароль.", Status.ERROR));
            }
        } else {
            throw new IllegalArgumentException("Не указаны данные авторизации.");
        }
    }
}
