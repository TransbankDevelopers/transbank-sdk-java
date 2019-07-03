package cl.transbank.webpay.exception;

import cl.transbank.webpay.oneclick.Oneclick;

public class InscriptionStartException extends Exception {
    public InscriptionStartException() { super(); }
    public InscriptionStartException(Exception e) { super(e); }
    public InscriptionStartException(String message) { super(message); }
}
