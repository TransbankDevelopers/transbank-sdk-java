package cl.transbank.onepay.util;

import cl.transbank.onepay.Onepay;
import cl.transbank.onepay.exception.SignException;
import cl.transbank.onepay.net.NullifyTransactionRequest;
import cl.transbank.onepay.net.GetTransactionNumberRequest;
import cl.transbank.onepay.net.SendTransactionRequest;
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

    public SendTransactionRequest sign(@NonNull SendTransactionRequest request, @NonNull String secret) throws SignException {
        String externalUniqueNumberAsString = String.valueOf(request.getExternalUniqueNumber());
        String totalAsString = String.valueOf(request.getTotal());
        String itemsQuantityAsString = String.valueOf(request.getItemsQuantity());
        String issuedAtAsString = String.valueOf(request.getIssuedAt());

        String data = externalUniqueNumberAsString.length() + externalUniqueNumberAsString;
        data += totalAsString.length() + totalAsString;
        data += itemsQuantityAsString.length() + itemsQuantityAsString;
        data += issuedAtAsString.length() + issuedAtAsString;
        data += Onepay.FAKE_CALLBACK_URL.length() + Onepay.FAKE_CALLBACK_URL;

        byte[] crypted = crypt(data, secret);

        request.setSignature(base64Encoder.encode(crypted));
        return request;
    }

    public GetTransactionNumberRequest sign(@NonNull GetTransactionNumberRequest request, @NonNull String secret) throws SignException {
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

    public NullifyTransactionRequest sign(@NonNull NullifyTransactionRequest request, @NonNull String secret)
        throws  SignException {
        String occ = request.getOcc();
        String externalUniqueNumber = request.getExternalUniqueNumber();
        String authorizationCode = request.getAuthorizationCode();
        String issuedAtAsString = String.valueOf(request.getIssuedAt());
        String nullifyAmountAsString = String.valueOf(request.getNullifyAmount());

        String data = occ.length() + occ;
        data += externalUniqueNumber.length() + externalUniqueNumber;
        data += authorizationCode.length() + authorizationCode;
        data += issuedAtAsString.length() + issuedAtAsString;
        data += nullifyAmountAsString.length() + nullifyAmountAsString;

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
