package cl.transbank.onepay.exception;

public class RefundCreateException extends TransbankException {
    public RefundCreateException() {
    }

    public RefundCreateException(int code, String message) {
        super(code, message);
    }

    public RefundCreateException(int code, String message, Throwable cause) {
        super(code, message, cause);
    }

    public RefundCreateException(Throwable cause) {
        super(cause);
    }

    public RefundCreateException(int code, Throwable cause) {
        super(code, cause);
    }

    public RefundCreateException(int code, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(code, message, cause, enableSuppression, writableStackTrace);
    }
}
