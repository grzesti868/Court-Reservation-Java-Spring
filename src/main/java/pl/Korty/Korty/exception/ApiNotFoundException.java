package pl.Korty.Korty.exception;

public class ApiNotFoundException extends RuntimeException{

    public ApiNotFoundException(String message) {
        super(message);
    }

    public ApiNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
