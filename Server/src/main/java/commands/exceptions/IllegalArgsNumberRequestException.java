package commands.exceptions;

/**
 * Исключение, вызываемое в случае неверного числа аргументов
 */
public class IllegalArgsNumberRequestException extends IllegalRequestException {
    public IllegalArgsNumberRequestException() {
    }

    public IllegalArgsNumberRequestException(String s) {
        super(s);
    }

    public IllegalArgsNumberRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalArgsNumberRequestException(Throwable cause) {
        super(cause);
    }
}