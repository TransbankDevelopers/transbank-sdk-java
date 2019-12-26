package cl.transbank.webpay.exception;

public class TransactionRefundException extends WebpayException {
    public TransactionRefundException() {
        super();
    }

    public TransactionRefundException(Exception e) {
        super(e);
    }

    public TransactionRefundException(String message) {
        super(message);
    }
}
