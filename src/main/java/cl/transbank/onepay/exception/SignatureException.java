package cl.transbank.onepay.exception;

public class SignatureException extends TransbankException {
    public SignatureException() {
    }

    public SignatureException(String message) {
        super(message);
    }

    public SignatureException(int code, String message) {
        super(code, message);
    }

    public SignatureException(int code, String message, Throwable cause) {
        super(code, message, cause);
    }

    public SignatureException(Throwable cause) {
        super(cause);
    }

    public SignatureException(int code, Throwable cause) {
        super(code, cause);
    }

    public SignatureException(int code, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(code, message, cause, enableSuppression, writableStackTrace);
    }
}
