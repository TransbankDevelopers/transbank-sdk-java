package cl.transbank.webpay.exception;

public class CreateTransactionException extends Exception {
    public CreateTransactionException() {
        super();
    }

    public CreateTransactionException(Exception e) {
        super(e);
    }

    public CreateTransactionException(String message) {
        super(message);
    }
}
