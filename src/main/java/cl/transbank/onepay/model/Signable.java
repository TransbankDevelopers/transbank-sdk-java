package cl.transbank.onepay.model;

public interface Signable {
    String getHashableString();
    void setSignature(String signature);
    String getSignature();
}
