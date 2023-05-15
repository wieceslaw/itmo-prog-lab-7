package se.ifmo.ru.client.requestbuilders;

import se.ifmo.ru.client.requestbuilders.exceptions.WrongArgumentsException;
import se.ifmo.ru.client.requestbuilders.exceptions.WrongArgumentsNumberException;
import se.ifmo.ru.common.dto.Response;
import se.ifmo.ru.common.dto.Status;
import se.ifmo.ru.client.talkers.Talker;
import se.ifmo.ru.client.util.Session;
import se.ifmo.ru.client.util.transceiving.Receiver;
import se.ifmo.ru.client.util.transceiving.Sender;

/**
 * Базовый класс сборщика запроса по команде
 */
public abstract class RequestBuilder {
    protected String name;
    protected int argsNumber;
    protected Talker talker;
    protected Sender sender;
    protected Receiver receiver;
    protected Session session;

    abstract public void execute(String[] args) throws WrongArgumentsNumberException, WrongArgumentsException;

    public String getName() {
        return name;
    }

    protected void show(Response response) {
        if (response != null) {
            if (!(response.getStatus() == Status.SUCCESS))
                talker.println("[ОШИБКА]: " + (response.getText() == null ? "Не удалось распознать ответ сервера." : response.getText()));
            else if (response.getText() != null) {
                talker.println(response.getText());
            }
            if (response.getElements() != null)
                response.getElements().forEach(x -> talker.println(x.toString()));
        }
    }
}
