package cl.transbank.webpay.exception;

public class AuthorizeTransactionException extends WebpayException {
    public AuthorizeTransactionException() {
    }

    public AuthorizeTransactionException(Exception e) {
        super(e);
    }

    public AuthorizeTransactionException(String message) {
        super(message);
    }
}
