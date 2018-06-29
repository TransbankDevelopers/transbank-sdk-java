package cl.transbank.onepay.util;

import lombok.NonNull;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

public class OnePaySignUtil {
    private Mac mac;
    private static final String CRYPT_ALGORITHM = "HMACSHA256";

    private static volatile OnePaySignUtil instance;

    public byte[] sign(@NonNull Object data, @NonNull String secretKey) throws InvalidKeyException {
        Key key = new SecretKeySpec(secretKey.getBytes(), CRYPT_ALGORITHM);
        mac.init(key);
        return mac.doFinal(data.toString().getBytes());
    }

    private OnePaySignUtil() throws NoSuchAlgorithmException {
        super();
        mac = Mac.getInstance(CRYPT_ALGORITHM);
    }

    public static OnePaySignUtil getInstance() throws NoSuchAlgorithmException {
        if (null == instance) {
            synchronized (OnePaySignUtil.class) {
                instance = new OnePaySignUtil();
            }
        }

        return instance;
    }
}
