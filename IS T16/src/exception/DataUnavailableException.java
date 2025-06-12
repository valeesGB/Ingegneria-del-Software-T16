package exception;

public class DataUnavailableException extends Exception {
    public DataUnavailableException(String message) {
        super(message);
    }

    public DataUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }

}
