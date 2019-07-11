package cl.transbank.webpay.exception;

import lombok.Getter;

public class WebpayException extends Exception {
    @Getter private int code;

    public WebpayException() {
        super();
    }

    public WebpayException(Exception e) {
        super(e);

        if (e instanceof WebpayHttpRuntimeException)
            this.code = ((WebpayHttpRuntimeException) e).getCode();
    }

    public WebpayException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return super.toString().concat(String.format(" | code : %s", code));
    }
}
