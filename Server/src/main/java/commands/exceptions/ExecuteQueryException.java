package commands.exceptions;

public class ExecuteQueryException extends RuntimeException {
    public ExecuteQueryException() {
    }

    public ExecuteQueryException(String message) {
        super(message);
    }

    public ExecuteQueryException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExecuteQueryException(Throwable cause) {
        super(cause);
    }
}
