package se.ifmo.ru.client.requestbuilders;

import se.ifmo.ru.client.talkers.Talker;
import se.ifmo.ru.common.dto.Request;
import se.ifmo.ru.client.requestbuilders.exceptions.WrongArgumentsNumberException;
import se.ifmo.ru.client.util.Session;
import se.ifmo.ru.client.util.transceiving.Receiver;
import se.ifmo.ru.client.util.transceiving.Sender;

/**
 * Класс сборщик запроса для запроса на сервер по команде AddIfMin
 */
public class AddIfMin extends RequestBuilder {
    
    public AddIfMin(Talker talker, Sender sender, Receiver receiver) {
        this.name = "add_if_min";
        this.argsNumber = 0;
        this.talker = talker;
        this.sender = sender;
        this.receiver = receiver;
        this.session = Session.getInstance();
    }

    @Override
    public void execute(String[] args) throws WrongArgumentsNumberException {
        if (args.length != argsNumber) throw new WrongArgumentsNumberException(String.valueOf(argsNumber));
        if (sender.send(new Request(name, args, talker.getOrganization(), session.getUser()))) {
            show(receiver.receive());
        }
    }
}
