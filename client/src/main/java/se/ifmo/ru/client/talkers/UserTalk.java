package se.ifmo.ru.client.talkers;

import se.ifmo.ru.common.model.User;

public class UserTalk implements Talk<User> {
    private final Talker talker;

    public UserTalk(Talker talker) {
        this.talker = talker;
    }

    @Override
    public User talk() {
        return new User(
                getLogin(),
                getPassword()
        );
    }

    public String getPassword() {
        talker.print("Введите пароль: ");
        return talker.readPassword();
    }

    public String getLogin() {
        talker.print("Введите логин: ");
        return talker.read();
    }
}
