package talkers;

import model.Organization;
import model.User;

import java.io.Console;

/**
 * Класс-обертка для общения с пользователем через {@link Console}
 */
public class ConsoleTalker implements Talker {
    private final Console console;
    private final OrganizationTalk organizationTalk;
    private final UserTalk userTalk;
    private boolean isScript = false;

    /**
     * Конструктор класса с вызовом системной консоли
     */
    public ConsoleTalker() {
        this.console = System.console();
        this.organizationTalk = new OrganizationTalk(this);
        this.userTalk = new UserTalk(this);
    }

    public ConsoleTalker(boolean isScript) {
        this.console = System.console();
        this.organizationTalk = new OrganizationTalk(this);
        this.userTalk = new UserTalk(this);
        this.isScript = isScript;
    }

    @Override
    public String read() {
        return console.readLine();
    }

    @Override
    public void println(String str) {
        console.printf(str + '\n');
    }

    @Override
    public void print(String str) {
        console.printf(str);
    }

    @Override
    public void printErr(String str) {
        console.printf("[ERROR]: " + str + '\n');
    }

    @Override
    public String readCommand() {
        if (!isScript) print("Введите команду: ");
        return read();
    }

    public Organization getOrganization() {
        return organizationTalk.talk();
    }

    @Override
    public String readPassword() {
        return new String(console.readPassword());
    }

    @Override
    public User getUser() {
        return userTalk.talk();
    }
}
