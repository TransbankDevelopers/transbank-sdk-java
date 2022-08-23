package cl.transbank.webpay.exception;

public class DeferredCaptureHistoryException extends WebpayException {
    public DeferredCaptureHistoryException() {
    }

    public DeferredCaptureHistoryException(Exception e) {
        super(e);
    }

    public DeferredCaptureHistoryException(String message) {
        super(message);
    }
}
