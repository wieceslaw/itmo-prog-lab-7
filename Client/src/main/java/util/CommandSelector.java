package util;

import requestbuilders.*;
import requestbuilders.exceptions.WrongArgumentsException;
import requestbuilders.exceptions.WrongArgumentsNumberException;
import talkers.Talker;
import util.transceiving.Receiver;
import util.transceiving.Sender;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Класс для выборки и исполнения команд
 */
public class CommandSelector {
    private final HashMap<String, RequestBuilder> requestBuilderHashMap = new HashMap<>();
    private final Talker talker;

    public CommandSelector(Sender sender, Receiver receiver, Talker talker) {
        this.talker = talker;
        addRequestBuilder(new Help(talker, sender, receiver));
        addRequestBuilder(new Info(talker, sender, receiver));
        addRequestBuilder(new Show(talker, sender, receiver));
        addRequestBuilder(new Add(talker, sender, receiver));
        addRequestBuilder(new Update(talker, sender, receiver));
        addRequestBuilder(new RemoveById(talker, sender, receiver));
        addRequestBuilder(new Clear(talker, sender, receiver));
        addRequestBuilder(new ExecuteScript(talker, sender, receiver));
        addRequestBuilder(new Exit(talker, sender, receiver));
        addRequestBuilder(new AddIfMax(talker, sender, receiver));
        addRequestBuilder(new AddIfMin(talker, sender, receiver));
        addRequestBuilder(new RemoveGreater(talker, sender, receiver));
        addRequestBuilder(new SumOfAnnualTurnover(talker, sender, receiver));
        addRequestBuilder(new PrintDescending(talker, sender, receiver));
        addRequestBuilder(new PrintFieldAscendingEmployeesCount(talker, sender, receiver));
    }

    private void addRequestBuilder(RequestBuilder requestBuilder) {
        requestBuilderHashMap.put(requestBuilder.getName(), requestBuilder);
    }

    /**
     * Вызывает метод {@link RequestBuilder#execute(String[]) execute} у {@link RequestBuilder}
     *
     * @param commandLine командная строка с именем команды и ее аргументами
     */
    public void execute(String commandLine) {
        String[] commandArgs = commandLine.trim().split("\\s+");
        String commandName = commandArgs[0];
        commandArgs = Arrays.copyOfRange(commandArgs, 1, commandArgs.length);
        if (!requestBuilderHashMap.containsKey(commandName)) {
            talker.println("Такой команды нет. Введите help, чтобы получить список и описание команд.");
        } else {
            try {
                requestBuilderHashMap.get(commandName).execute(commandArgs);
            } catch (WrongArgumentsNumberException e) {
                talker.println("Команда принимает число аргументов: " + e.getMessage());
            } catch (WrongArgumentsException e) {
                talker.println(e.getMessage());
            }
        }
    }
}
