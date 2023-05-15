package se.ifmo.ru.common.dto;

import se.ifmo.ru.common.model.Organization;
import se.ifmo.ru.common.model.User;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Класс передачи данных о запросах с клиента
 */
public class Request implements Serializable {
    private final String command;
    private final String[] args;
    private final Organization organization;
    private final User user;

    public Request(String command, String[] args, Organization organization, User user) {
        this.command = command;
        this.args = args;
        this.organization = organization;
        this.user = user;
    }

    @Override
    public String toString() {
        return "Request{" +
                "command='" + command + '\'' +
                ", args=" + Arrays.toString(args) +
                ", organization=" + organization +
                ", user=" + user +
                '}';
    }

    public String getCommand() {
        return command;
    }

    public String[] getArgs() {
        return args;
    }

    public Organization getOrganization() {
        return organization;
    }

    public User getUser() {
        return user;
    }
}
