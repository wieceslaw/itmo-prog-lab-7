package model.exceptions;

import model.Organization;

/**
 * Исключение, вызываемое при ошибке сборки объекта класса {@link Organization}
 */
public class ElementConstructorException extends RuntimeException {
    public ElementConstructorException() {
    }

    public ElementConstructorException(String message) {
        super(message);
    }

    public ElementConstructorException(String message, Throwable cause) {
        super(message, cause);
    }

    public ElementConstructorException(Throwable cause) {
        super(cause);
    }
}
