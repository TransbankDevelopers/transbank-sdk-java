package cl.transbank.onepay.util;

import cl.transbank.onepay.Onepay;
import cl.transbank.onepay.exception.SignException;
import cl.transbank.onepay.model.TransactionCommitRequest;
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

    public byte[] crypt(@NonNull Object data, @NonNull String secretKey) throws SignException {
        Key key = new SecretKeySpec(secretKey.getBytes(), CRYPT_ALGORITHM);
        try {
            mac.init(key);
        } catch (InvalidKeyException e) {
            throw new SignException(e);
        }
        return mac.doFinal(data.toString().getBytes());
    }

    public TransactionCreateRequest sign(@NonNull TransactionCreateRequest request, @NonNull String secret) throws SignException {
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

    public TransactionCommitRequest sign(@NonNull TransactionCommitRequest request, @NonNull String secret) throws SignException {
        String occ = request.getOcc();
        String externalUniqueNumber = request.getExternalUniqueNumber();
        String issuedAtAsString = String.valueOf(request.getIssuedAt());

        String data = occ.length() + occ;
        data += externalUniqueNumber.length() + externalUniqueNumber;
        data += issuedAtAsString.length() + issuedAtAsString;

        byte[] crypted = crypt(data, secret);

        request.setSignature(base64Encoder.encode(crypted));
        return request;
    }

    private OnePaySignUtil() throws SignException {
        super();
        try {
            mac = Mac.getInstance(CRYPT_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            throw new SignException(e);
        }
        base64Encoder = new BASE64Encoder();
    }

    public static OnePaySignUtil getInstance() throws SignException {
        if (null == instance) {
            synchronized (OnePaySignUtil.class) {
                instance = new OnePaySignUtil();
            }
        }

        return instance;
    }
}
