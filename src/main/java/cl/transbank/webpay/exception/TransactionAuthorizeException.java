package cl.transbank.webpay.exception;

public class TransactionAuthorizeException extends WebpayException {
    public TransactionAuthorizeException() {
    }

    public TransactionAuthorizeException(Exception e) {
        super(e);
    }

    public TransactionAuthorizeException(String message) {
        super(message);
    }
}
