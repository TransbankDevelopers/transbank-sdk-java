package cl.transbank.onepay.exception;

public class SignException extends Throwable {
    public SignException() {
    }

    public SignException(String message) {
        super(message);
    }

    public SignException(String message, Throwable cause) {
        super(message, cause);
    }

    public SignException(Throwable cause) {
        super(cause);
    }

    public SignException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
