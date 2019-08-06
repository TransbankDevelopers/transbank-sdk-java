package cl.transbank.webpay.exception;

import cl.transbank.exception.TransbankException;
import lombok.Getter;

public class TransbankHttpApiException extends WebpayException {
    public TransbankHttpApiException() {
    }

    public TransbankHttpApiException(Exception e) {
        super(e);
    }

    public TransbankHttpApiException(String message) {
        super(message);
    }

    public TransbankHttpApiException(int code, String message) {
        super(code, message);
    }
}
