package cl.transbank.webpay.exception;

public class InscriptionDeleteException extends WebpayException {
    public InscriptionDeleteException() {
    }

    public InscriptionDeleteException(Exception e) {
        super(e);
    }

    public InscriptionDeleteException(String message) {
        super(message);
    }
}
