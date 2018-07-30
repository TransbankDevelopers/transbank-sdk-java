package cl.transbank.exception;

public class TransbankException extends Throwable {
    private int code;

    public TransbankException() {
        super();
    }

    public TransbankException(String message) {
        super(message);
        this.code = -1;
    }

    public TransbankException(int code, String message) {
        super(message);
        this.code = code;
    }

    public TransbankException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public TransbankException(Throwable cause) {
        super(cause);
    }

    public TransbankException(int code, Throwable cause) {
        super(cause);
        this.code = code;
    }

    public TransbankException(int code, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
    }
}
