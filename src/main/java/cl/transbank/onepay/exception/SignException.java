package cl.transbank.onepay.exception;

public class SignException extends TransbankException {
    public SignException() {
    }

    public SignException(int code, String message) {
        super(code, message);
    }

    public SignException(int code, String message, Throwable cause) {
        super(code, message, cause);
    }

    public SignException(Throwable cause) {
        super(cause);
    }

    public SignException(int code, Throwable cause) {
        super(code, cause);
    }

    public SignException(int code, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(code, message, cause, enableSuppression, writableStackTrace);
    }
}
