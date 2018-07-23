package cl.transbank.onepay.util;

import cl.transbank.onepay.exception.SignatureException;
import cl.transbank.onepay.model.Signable;
import cl.transbank.onepay.model.TransactionCreateResponse;
import cl.transbank.onepay.net.*;
import lombok.NonNull;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

public class OnePaySignUtil {
    private Mac mac;
    private static final String CRYPT_ALGORITHM = "HMACSHA256";
    private BASE64Encoder base64Encoder;

    private static volatile OnePaySignUtil instance;

    public byte[] crypt(@NonNull Object data, @NonNull String secretKey) throws SignatureException {
        Key key = new SecretKeySpec(secretKey.getBytes(), CRYPT_ALGORITHM);
        try {
            mac.init(key);
        } catch (InvalidKeyException e) {
            throw new SignatureException(e);
        }
        return mac.doFinal(data.toString().getBytes());
    }

    public <T> T sign(@NonNull Signable signable, @NonNull String secret, Class<T> type) throws SignatureException {
        byte[] crypted = crypt(signable.getHash(), secret);
        signable.setSignature(base64Encoder.encode(crypted));
        return type.cast(signable);
    }

    public boolean validate(@NonNull Signable signable, String secret) throws SignatureException {
        byte[] crypted = crypt(signable.getHash(), secret);
        String sign = base64Encoder.encode(crypted);
        return sign.equals(signable.getSignature());
    }

    private OnePaySignUtil() throws SignatureException {
        super();
        try {
            mac = Mac.getInstance(CRYPT_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            throw new SignatureException(e);
        }
        base64Encoder = new BASE64Encoder();
    }

    public static OnePaySignUtil getInstance() throws SignatureException {
        if (null == instance) {
            synchronized (OnePaySignUtil.class) {
                instance = new OnePaySignUtil();
            }
        }

        return instance;
    }
}
