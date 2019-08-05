package cl.transbank.webpay.exception;

public class InscriptionFinishException extends WebpayException {
    public InscriptionFinishException() {
    }

    public InscriptionFinishException(Exception e) {
        super(e);
    }

    public InscriptionFinishException(String message) {
        super(message);
    }
}
