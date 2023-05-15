package se.ifmo.ru.client.requestbuilders;

import se.ifmo.ru.client.requestbuilders.exceptions.WrongArgumentsNumberException;
import se.ifmo.ru.common.dto.Request;
import se.ifmo.ru.client.talkers.Talker;
import se.ifmo.ru.client.util.Session;
import se.ifmo.ru.client.util.transceiving.Receiver;
import se.ifmo.ru.client.util.transceiving.Sender;

/**
 * Класс сборщик запроса для запроса на сервер по команде PrintFieldAscendingEmployeesCount
 */
public class PrintFieldAscendingEmployeesCount extends RequestBuilder {
    public PrintFieldAscendingEmployeesCount(Talker talker, Sender sender, Receiver receiver) {
        this.name = "print_field_ascending_employees_count";
        this.argsNumber = 0;
        this.talker = talker;
        this.sender = sender;
        this.receiver = receiver;
        this.session = Session.getInstance();
    }

    @Override
    public void execute(String[] args) throws WrongArgumentsNumberException {
        if (args.length != argsNumber) throw new WrongArgumentsNumberException(String.valueOf(argsNumber));
        if (sender.send(new Request(name, args, null, session.getUser()))) {
            show(receiver.receive());
        }
    }
}
