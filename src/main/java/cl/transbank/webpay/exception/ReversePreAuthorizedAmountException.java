package cl.transbank.webpay.exception;

public class ReversePreAuthorizedAmountException extends WebpayException {
    public ReversePreAuthorizedAmountException() {
    }

    public ReversePreAuthorizedAmountException(Exception e) {
        super(e);
    }

    public ReversePreAuthorizedAmountException(String message) {
        super(message);
    }
}
