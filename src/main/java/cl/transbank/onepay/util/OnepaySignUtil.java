package cl.transbank.onepay.util;

import cl.transbank.onepay.exception.SignatureException;
import cl.transbank.onepay.model.Signable;
import lombok.NonNull;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

public class OnepaySignUtil implements SignUtil {
    private Mac mac;
    private static final String CRYPT_ALGORITHM = "HMACSHA256";
    private BASE64Encoder base64Encoder;

    private static volatile OnepaySignUtil instance;

    private byte[] crypt(@NonNull Object data, @NonNull String secretKey) throws SignatureException {
        Key key = new SecretKeySpec(secretKey.getBytes(), CRYPT_ALGORITHM);
        try {
            mac.init(key);
        } catch (InvalidKeyException e) {
            throw new SignatureException(e);
        }
        return mac.doFinal(data.toString().getBytes());
    }

    public void sign(@NonNull Signable signable, @NonNull String secret) throws SignatureException {
        byte[] crypted = crypt(signable.getHashableString(), secret);
        signable.setSignature(base64Encoder.encode(crypted));
    }

    public boolean validate(@NonNull Signable signable, String secret) throws SignatureException {
        byte[] crypted = crypt(signable.getHashableString(), secret);
        String sign = base64Encoder.encode(crypted);
        return sign.equals(signable.getSignature());
    }

    private OnepaySignUtil() {
        super();
        try {
            mac = Mac.getInstance(CRYPT_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            throw new ExceptionInInitializerError(e);
        }
        base64Encoder = new BASE64Encoder();
    }

    public static OnepaySignUtil getInstance() {
        if (null == instance) {
            synchronized (OnepaySignUtil.class) {
                instance = new OnepaySignUtil();
            }
        }

        return instance;
    }
}
