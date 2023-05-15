package se.ifmo.ru.client.requestbuilders.exceptions;

/**
 * Исключение, вызываемое в случае неверного числа передаваемых аргументов
 */
public class WrongArgumentsNumberException extends Exception {
    public WrongArgumentsNumberException() {
    }

    public WrongArgumentsNumberException(String message) {
        super(message);
    }

    public WrongArgumentsNumberException(String message, Throwable cause) {
        super(message, cause);
    }

    public WrongArgumentsNumberException(Throwable cause) {
        super(cause);
    }
}
