package cl.transbank.onepay.util;

import cl.transbank.onepay.Onepay;
import cl.transbank.onepay.model.TransactionCreateRequest;
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

    public byte[] crypt(@NonNull Object data, @NonNull String secretKey) throws InvalidKeyException {
        Key key = new SecretKeySpec(secretKey.getBytes(), CRYPT_ALGORITHM);
        mac.init(key);
        return mac.doFinal(data.toString().getBytes());
    }

    public TransactionCreateRequest sign(@NonNull TransactionCreateRequest request, @NonNull String secret) throws InvalidKeyException {
        String externalUniqueNumberAsString = String.valueOf(request.getExternalUniqueNumber());
        String totalAsString = String.valueOf(request.getTotal());
        String itemsQuantityAsString = String.valueOf(request.getItemsQuantity());
        String issuedAtAsString = String.valueOf(request.getIssuedAt());

        String data = externalUniqueNumberAsString.length() + externalUniqueNumberAsString;
        data += totalAsString.length() + totalAsString;
        data += itemsQuantityAsString.length() + itemsQuantityAsString;
        data += issuedAtAsString.length() + issuedAtAsString;
        data += Onepay.getCallbackUrl().length() + Onepay.getCallbackUrl();

        byte[] crypted = crypt(data, secret);

        request.setSignature(base64Encoder.encode(crypted));
        return request;
    }

    private OnePaySignUtil() throws NoSuchAlgorithmException {
        super();
        mac = Mac.getInstance(CRYPT_ALGORITHM);
        base64Encoder = new BASE64Encoder();
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
