package requestbuilders;

import requestbuilders.exceptions.WrongArgumentsException;
import dto.Request;
import requestbuilders.exceptions.WrongArgumentsNumberException;
import talkers.Talker;
import util.Session;
import util.transceiving.Receiver;
import util.transceiving.Sender;

/**
 * Класс сборщик запроса для запроса на сервер по команде Update
 */
public class Update extends RequestBuilder {
    
    public Update(Talker talker, Sender sender, Receiver receiver) {
        this.name = "update";
        this.argsNumber = 1;
        this.talker = talker;
        this.sender = sender;
        this.receiver = receiver;
        this.session = Session.getInstance();
    }

    @Override
    public void execute(String[] args) throws WrongArgumentsNumberException, WrongArgumentsException {
        if (args.length != argsNumber) throw new WrongArgumentsNumberException(String.valueOf(argsNumber));
        try {
            Long id = Long.parseLong(args[0]);
            if (id < 0) throw new WrongArgumentsException("Команда не принимает отрицательный ID.");
        } catch (NumberFormatException e) {
            throw new WrongArgumentsException("Неверный формат ID");
        }
        if (sender.send(new Request(name, args, talker.getOrganization(), session.getUser()))) {
            show(receiver.receive());
        }
    }
}
