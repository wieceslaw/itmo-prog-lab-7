package requestbuilders.exceptions;

/**
 * Исключение, вызываемое в случае передачи неверных аргументов
 */
public class WrongArgumentsException extends Exception {
    public WrongArgumentsException() {
    }
    public WrongArgumentsException(String message) {
        super(message);
    }

    public WrongArgumentsException(String message, Throwable cause) {
        super(message, cause);
    }

    public WrongArgumentsException(Throwable cause) {
        super(cause);
    }
}
