package cl.transbank.webpay.exception;

public class IncreaseAuthorizationDateException extends WebpayException {
    public IncreaseAuthorizationDateException() {
    }

    public IncreaseAuthorizationDateException(Exception e) {
        super(e);
    }

    public IncreaseAuthorizationDateException(String message) {
        super(message);
    }
}
