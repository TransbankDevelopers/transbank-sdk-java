package cl.transbank.webpay.exception;

public class WebpayException extends Exception {
    public WebpayException() {
        super();
    }

    public WebpayException(Exception e) {
        super(e);
    }

    public WebpayException(String message) {
        super(message);
    }
}
