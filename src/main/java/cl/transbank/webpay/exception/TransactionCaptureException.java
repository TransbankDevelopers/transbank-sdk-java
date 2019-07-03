package cl.transbank.webpay.exception;

public class TransactionCaptureException extends Exception {
    public TransactionCaptureException() {
        super();
    }

    public TransactionCaptureException(Exception e) {
        super(e);
    }

    public TransactionCaptureException(String message) {
        super(message);
    }
}
