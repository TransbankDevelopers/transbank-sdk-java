package cl.transbank.webpay.exception;

public class TransactionCreateException extends Exception {
    public TransactionCreateException() {
        super();
    }

    public TransactionCreateException(Exception e) {
        super(e);
    }

    public TransactionCreateException(String message) {
        super(message);
    }
}
