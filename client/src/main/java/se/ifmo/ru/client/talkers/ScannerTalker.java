package se.ifmo.ru.client.talkers;

import java.util.Scanner;
import se.ifmo.ru.common.model.Organization;
import se.ifmo.ru.common.model.User;

/**
 * Класс-обертка для общения с пользователем через сканнер {@link Scanner}
 */
public class ScannerTalker implements Talker {
    private final Scanner scanner;
    private OrganizationTalk organizationTalk;
    private UserTalk userTalk;
    private boolean isScript = false;

    public ScannerTalker() {
        this.scanner = new Scanner(System.in);
        this.organizationTalk = new OrganizationTalk(this);
        this.userTalk = new UserTalk(this);
    }

    public ScannerTalker(Scanner scanner, boolean isScript) {
        this.scanner = scanner;
        this.organizationTalk = new OrganizationTalk(this);
        this.userTalk = new UserTalk(this);
        this.isScript = isScript;
    }

    @Override
    public String read() {
        if (scanner.hasNextLine()) {
            return scanner.nextLine();
        } else {
            System.out.println("Конец ввода.");
            System.exit(0);
        }
        return null;
    }

    @Override
    public void println(String str) {
//        if (!isScript) System.out.println(str);
        System.out.println(str);
    }

    @Override
    public void print(String str) {
//        if (!isScript) System.out.print(str);
        System.out.print(str);
    }

    @Override
    public void printErr(String str) {
//        if (!isScript) System.out.println("[ERROR]: " + str);
        System.out.println("[ERROR]: " + str);
    }

    @Override
    public String readCommand() {
        print("Введите команду: ");
        return read();
    }

    @Override
    public Organization getOrganization() {
        return organizationTalk.talk();
    }

    @Override
    public String readPassword() {
        return scanner.nextLine();
    }

    @Override
    public User getUser() {
        return userTalk.talk();
    }
}
