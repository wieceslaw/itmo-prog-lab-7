package requestbuilders;

import dto.Response;
import dto.Status;
import requestbuilders.exceptions.WrongArgumentsException;
import requestbuilders.exceptions.WrongArgumentsNumberException;
import talkers.Talker;
import util.Session;
import util.transceiving.Receiver;
import util.transceiving.Sender;

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
