package cl.transbank.webpay.exception;

public class CaptureTransactionException extends WebpayException {
    public CaptureTransactionException() {
        super();
    }

    public CaptureTransactionException(Exception e) {
        super(e);
    }

    public CaptureTransactionException(String message) {
        super(message);
    }
}
