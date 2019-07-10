package cl.transbank.webpay.exception;

public class RefundTransactionException extends WebpayException {
    public RefundTransactionException() {
        super();
    }

    public RefundTransactionException(Exception e) {
        super(e);
    }

    public RefundTransactionException(String message) {
        super(message);
    }
}
