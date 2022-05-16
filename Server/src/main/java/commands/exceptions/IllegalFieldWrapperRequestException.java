package commands.exceptions;

/**
 * Исключение, вызываемое в случае ошибочного наличия или отсутствия {@link exchange.request.FieldsWrapper}
 */
public class IllegalFieldWrapperRequestException extends IllegalRequestException {
    public IllegalFieldWrapperRequestException() {
    }

    public IllegalFieldWrapperRequestException(String s) {
        super(s);
    }

    public IllegalFieldWrapperRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalFieldWrapperRequestException(Throwable cause) {
        super(cause);
    }
}
