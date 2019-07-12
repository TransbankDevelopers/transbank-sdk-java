package cl.transbank.webpay.exception;

public class FinishInscriptionException extends WebpayException {
    public FinishInscriptionException() {
    }

    public FinishInscriptionException(Exception e) {
        super(e);
    }

    public FinishInscriptionException(String message) {
        super(message);
    }
}
