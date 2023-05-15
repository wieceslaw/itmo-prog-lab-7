package se.ifmo.ru.server.util.crypto;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encryptor {
    private MessageDigest sha1 = null;
    private String salt;

    public Encryptor() {
        try {
            this.sha1 = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public Encryptor(String salt) throws NoSuchAlgorithmException {
        try {
            this.sha1 = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        this.salt = salt;
    }

    public String generateHashForPassword(String password) {
        if (salt != null) return new String(sha1.digest((password + salt).getBytes()));
        return new String(sha1.digest(password.getBytes()));
    }
}
