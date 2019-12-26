package cl.transbank.webpay.exception;

public class InscriptionStartException extends WebpayException {
    public InscriptionStartException() { super(); }
    public InscriptionStartException(Exception e) { super(e); }
    public InscriptionStartException(String message) { super(message); }
}
