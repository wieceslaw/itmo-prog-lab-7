package se.ifmo.ru.client.talkers;

import se.ifmo.ru.common.model.Organization;
import se.ifmo.ru.common.model.User;

/**
 * Интерфейс-обертка для общения с пользователем
 */
public interface Talker {
    /**
     * Считать строку
     *
     * @return считанная строка
     */
    String read();

    String readPassword();

    /**
     * Напечатать строку с переносом
     */
    void println(String str);

    /**
     * Напечатать строку без переноса
     */
    void print(String str);

    void printErr(String str);

    /**
     * Считать команду с префиксом
     *
     * @return Считанная командная строка
     */
    String readCommand();

    Organization getOrganization();

    User getUser();
}
