package cl.transbank.onepay.util;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface Signable<T> {
    T sign() throws NoSuchAlgorithmException, InvalidKeyException;
}
