package cl.transbank.webpay.exception;

public class TransactionCaptureException extends WebpayException {
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
