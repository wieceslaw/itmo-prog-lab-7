package util;

import dto.Request;
import dto.Response;
import dto.Status;
import model.User;
import talkers.Talker;
import util.transceiving.Receiver;
import util.transceiving.Sender;

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
        String action =  talker.read().trim().toLowerCase();
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
