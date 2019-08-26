package ua.nure.koshevyi.exception;

public class AppException extends Exception {

    private static final long serialVersionUID = 6685843629178076531L;

    public AppException() {
        super();
    }

    public AppException(String message, Throwable cause) {
        super(message, cause);
    }

    public AppException(String message) {
        super(message);
    }
}