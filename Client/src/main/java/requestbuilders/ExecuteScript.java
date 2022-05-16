package requestbuilders;

import util.CommandSelector;
import requestbuilders.exceptions.WrongArgumentsNumberException;
import talkers.ScannerTalker;
import talkers.Talker;
import util.Session;
import util.transceiving.Receiver;
import util.transceiving.Sender;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.Stack;

/**
 * Класс сборщик запроса для запроса на сервер по команде ExecuteScript
 */
public class ExecuteScript extends RequestBuilder {
    public static Stack<String> scriptsStack = new Stack<String>();

    public ExecuteScript(Talker talker, Sender sender, Receiver receiver) {
        this.name = "execute_script";
        this.argsNumber = 1;
        this.talker = talker;
        this.sender = sender;
        this.receiver = receiver;
        this.session = Session.getInstance();
    }

    @Override
    public void execute(String[] args) throws WrongArgumentsNumberException {
        if (args.length != argsNumber) throw new WrongArgumentsNumberException(String.valueOf(argsNumber));
        String fileName = args[0];
        if (scriptsStack.contains(fileName)) {
            talker.println("Скрипт " + Paths.get(fileName).getFileName() + " уже вызван.");
        } else {
            scriptsStack.push(fileName);
            try {
                Scanner newScanner = new Scanner(new File(fileName));
                Talker scannerTalker = new ScannerTalker(newScanner, true);
                CommandSelector commandSelector = new CommandSelector(sender, receiver, scannerTalker);
                while (newScanner.hasNextLine()) {
                    String str = newScanner.nextLine().trim();
                    commandSelector.execute(str);
                }
                talker.println("Файл " + Paths.get(fileName).getFileName() + " успешно исполнен.");
            } catch (NullPointerException | FileNotFoundException e) {
                talker.println("Нет указанного файла ");
            }
            scriptsStack.pop();
        }
    }
}
