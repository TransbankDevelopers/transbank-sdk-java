package cl.transbank.webpay.exception;

public class TransactionCreateException extends WebpayException {
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
