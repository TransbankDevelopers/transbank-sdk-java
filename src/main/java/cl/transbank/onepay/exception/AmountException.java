package cl.transbank.onepay.exception;

public class AmountException extends TransbankException {
    public AmountException() {
    }

    public AmountException(String message) {
        super(message);
    }

    public AmountException(int code, String message) {
        super(code, message);
    }

    public AmountException(int code, String message, Throwable cause) {
        super(code, message, cause);
    }

    public AmountException(Throwable cause) {
        super(cause);
    }

    public AmountException(int code, Throwable cause) {
        super(code, cause);
    }

    public AmountException(int code, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(code, message, cause, enableSuppression, writableStackTrace);
    }
}
