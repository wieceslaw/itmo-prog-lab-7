package requestbuilders;

import talkers.Talker;
import dto.Request;
import requestbuilders.exceptions.WrongArgumentsNumberException;
import util.Session;
import util.transceiving.Receiver;
import util.transceiving.Sender;

/**
 * Класс сборщик запроса для запроса на сервер по команде Add
 */
public class Add extends RequestBuilder {
    public Add(Talker talker, Sender sender, Receiver receiver) {
        this.name = "add";
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
