package cl.transbank.onepay.model;

import cl.transbank.onepay.exception.SignatureException;

public interface Signable {
    String getHashableString() throws SignatureException;
    void setSignature(String signature);
    String getSignature();
}
