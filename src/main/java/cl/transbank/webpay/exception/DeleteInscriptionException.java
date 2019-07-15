package cl.transbank.webpay.exception;

public class DeleteInscriptionException extends WebpayException {
    public DeleteInscriptionException() {
    }

    public DeleteInscriptionException(Exception e) {
        super(e);
    }

    public DeleteInscriptionException(String message) {
        super(message);
    }
}
