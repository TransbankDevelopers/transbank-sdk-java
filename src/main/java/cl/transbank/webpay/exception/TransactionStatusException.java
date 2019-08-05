package cl.transbank.webpay.exception;

public class TransactionStatusException extends WebpayException {
    public TransactionStatusException() {
        super();
    }

    public TransactionStatusException(Exception e) {
        super(e);
    }

    public TransactionStatusException(String message) {
        super(message);
    }
}
