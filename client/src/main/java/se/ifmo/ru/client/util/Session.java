package se.ifmo.ru.client.util;

import se.ifmo.ru.client.talkers.Talker;
import se.ifmo.ru.client.util.transceiving.Receiver;
import se.ifmo.ru.client.util.transceiving.Sender;
import se.ifmo.ru.common.dto.Request;
import se.ifmo.ru.common.dto.Response;
import se.ifmo.ru.common.dto.Status;
import se.ifmo.ru.common.model.User;

public class Session {
    private static Session session;
    private User user;
    private boolean isOpened;

    private Session() {
    }

    public static Session getInstance() {
        if (session == null) {
            session = new Session();
        }
        return session;
    }

    public void openSession(Talker talker, Sender sender, Receiver receiver) {
        talker.print("Для регистрации введите \"регистрация\", для входа - \"вход\": ");
        String action = talker.read().trim().toLowerCase();
        switch (action) {
            case "вход":
                login(talker, sender, receiver);
                break;
            case "регистрация":
                registration(talker, sender, receiver);
                break;
            default:
                talker.println("Неверная команда.");
        }
    }

    public void registration(Talker talker, Sender sender, Receiver receiver) {
        user = talker.getUser();
        Response response = null;
        if (sender.send(new Request("register", new String[0], null, user))) {
            response = receiver.receive();
        }
        if (response != null) {
            if (response.getStatus() == Status.SUCCESS) {
                talker.println(response.getText());
                isOpened = true;
            } else {
                talker.printErr(response.getText());
            }
        }
    }

    public void login(Talker talker, Sender sender, Receiver receiver) {
        user = talker.getUser();
        Response response = null;
        if (sender.send(new Request("login", new String[0], null, user))) {
            response = receiver.receive();
        }
        if (response != null) {
            if (response.getStatus() == Status.SUCCESS) {
                talker.println(response.getText());
                isOpened = true;
            } else {
                talker.printErr(response.getText());
            }
        }
    }

    public boolean isOpened() {
        return isOpened;
    }

    public User getUser() {
        return user;
    }
}
