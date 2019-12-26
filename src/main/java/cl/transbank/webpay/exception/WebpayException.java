package cl.transbank.webpay.exception;

import cl.transbank.exception.TransbankException;

public class WebpayException extends TransbankException {
    public WebpayException() {
        super();
    }

    public WebpayException(Exception e) {
        super(e);
    }

    public WebpayException(String message) {
        super(message);
    }

    public WebpayException(int code, String message) {
        super(code, message);
    }

    @Override
    public String toString() {
        return super.toString().concat(String.format(" | code : %s", getCode()));
    }
}
