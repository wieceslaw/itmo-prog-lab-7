package se.ifmo.ru.server.commands.exceptions;

import se.ifmo.ru.common.dto.Request;

/**
 * Базовое исключение при работе с запросами {@link Request} клиента
 */
public class IllegalRequestException extends IllegalArgumentException {
    public IllegalRequestException() {
    }

    public IllegalRequestException(String s) {
        super(s);
    }

    public IllegalRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalRequestException(Throwable cause) {
        super(cause);
    }
}
