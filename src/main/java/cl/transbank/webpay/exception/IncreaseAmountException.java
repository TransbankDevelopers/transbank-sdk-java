package cl.transbank.webpay.exception;

public class IncreaseAmountException extends WebpayException {
    public IncreaseAmountException() {
    }

    public IncreaseAmountException(Exception e) {
        super(e);
    }

    public IncreaseAmountException(String message) {
        super(message);
    }
}
