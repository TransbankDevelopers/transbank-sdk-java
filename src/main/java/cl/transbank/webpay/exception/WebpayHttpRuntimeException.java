package cl.transbank.webpay.exception;

import lombok.Getter;

public class WebpayHttpRuntimeException extends RuntimeException {
    @Getter private int code;

    public WebpayHttpRuntimeException() {
    }

    public WebpayHttpRuntimeException(String message) {
        super(message);
    }

    public WebpayHttpRuntimeException(String message, int code) {
        super(message);
        this.code = code;
    }

    public WebpayHttpRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public WebpayHttpRuntimeException(Throwable cause) {
        super(cause);
    }

    public WebpayHttpRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
