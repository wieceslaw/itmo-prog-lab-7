package se.ifmo.ru.server.util.exceptions;

/**
 * Исключение, вызываемое при работе с коллекцией, в случае, когда нарушается единственность элемента с определенным ID
 */
public class UniqueElementException extends RuntimeException {
    public UniqueElementException() {
    }

    public UniqueElementException(String message) {
        super(message);
    }

    public UniqueElementException(String message, Throwable cause) {
        super(message, cause);
    }

    public UniqueElementException(Throwable cause) {
        super(cause);
    }
}
