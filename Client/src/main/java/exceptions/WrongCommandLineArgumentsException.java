package exceptions;

public class WrongCommandLineArgumentsException extends Exception {
    public WrongCommandLineArgumentsException() {
    }

    public WrongCommandLineArgumentsException(String message) {
        super(message);
    }

    public WrongCommandLineArgumentsException(String message, Throwable cause) {
        super(message, cause);
    }

    public WrongCommandLineArgumentsException(Throwable cause) {
        super(cause);
    }
}
