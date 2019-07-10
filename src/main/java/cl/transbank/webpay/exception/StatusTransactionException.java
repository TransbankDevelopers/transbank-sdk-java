package cl.transbank.webpay.exception;

public class StatusTransactionException extends WebpayException {
    public StatusTransactionException() {
        super();
    }

    public StatusTransactionException(Exception e) {
        super(e);
    }

    public StatusTransactionException(String message) {
        super(message);
    }
}
