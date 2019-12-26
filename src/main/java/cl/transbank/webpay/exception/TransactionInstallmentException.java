package cl.transbank.webpay.exception;

public class TransactionInstallmentException extends WebpayException {
    public TransactionInstallmentException() {
        super();
    }

    public TransactionInstallmentException(Exception e) {
        super(e);
    }

    public TransactionInstallmentException(String message) {
        super(message);
    }
}
