package server;

public class MyAppException extends Exception {
    public MyAppException(String message, Throwable cause) {
        super(message, cause);
    }
}
