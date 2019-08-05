package cl.transbank.webpay.exception;

public class TransactionCommitException extends WebpayException {
    public TransactionCommitException() {
        super();
    }

    public TransactionCommitException(Exception e) {
        super(e);
    }

    public TransactionCommitException(String message) {
        super(message);
    }
}
