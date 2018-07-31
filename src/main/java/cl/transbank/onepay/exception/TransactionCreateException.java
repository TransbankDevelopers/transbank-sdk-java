package cl.transbank.onepay.exception;

import cl.transbank.exception.TransbankException;

public class TransactionCreateException extends TransbankException {
    public TransactionCreateException() {
    }

    public TransactionCreateException(String message) {
        super(message);
    }

    public TransactionCreateException(int code, String message) {
        super(code, message);
    }

    public TransactionCreateException(int code, String message, Throwable cause) {
        super(code, message, cause);
    }

    public TransactionCreateException(Throwable cause) {
        super(cause);
    }

    public TransactionCreateException(int code, Throwable cause) {
        super(code, cause);
    }

    public TransactionCreateException(int code, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(code, message, cause, enableSuppression, writableStackTrace);
    }
}
