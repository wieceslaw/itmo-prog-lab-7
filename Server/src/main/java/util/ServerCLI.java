package util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

public class ServerCLI implements Runnable {
    private final Scanner scanner;
    private final Logger logger = LogManager.getLogger();

    public ServerCLI() {
        scanner = new Scanner(System.in);
    }

    @Override
    public void run() {
        while (true) {
            if (scanner.nextLine().trim().equals("exit")) {
                logger.info("Завершение работы сервера.");
                System.exit(0);
            } else {
                logger.warn("Такой команды нет, для завершения работы введите \"exit\"");
            }
        }
    }
}
