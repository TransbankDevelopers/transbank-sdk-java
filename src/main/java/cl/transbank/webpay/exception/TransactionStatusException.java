package cl.transbank.webpay.exception;

public class TransactionStatusException extends Exception {
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
