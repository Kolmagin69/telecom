package intech.com.exception;

public class ThreadsRunningException extends RuntimeException {

    public ThreadsRunningException(String message) {
        super(message);
    }
}
