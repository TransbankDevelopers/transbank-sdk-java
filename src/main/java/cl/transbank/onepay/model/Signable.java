package cl.transbank.onepay.model;

import cl.transbank.onepay.exception.SignatureException;

public interface Signable {
    String getHash() throws SignatureException;
    void setSignature(String signature);
    String getSignature();
}
