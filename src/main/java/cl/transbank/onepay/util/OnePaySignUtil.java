package cl.transbank.onepay.util;

import cl.transbank.onepay.Onepay;
import cl.transbank.onepay.exception.SignatureException;
import cl.transbank.onepay.model.TransactionCreateResponse;
import cl.transbank.onepay.net.GetTransactionNumberRequest;
import cl.transbank.onepay.net.SendTransactionRequest;
import cl.transbank.onepay.net.SendTransactionResponse;
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

    public SendTransactionRequest sign(@NonNull SendTransactionRequest request, @NonNull String secret) throws SignatureException {
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

    public GetTransactionNumberRequest sign(@NonNull GetTransactionNumberRequest request, @NonNull String secret) throws SignatureException {
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

    public boolean validate(@NonNull SendTransactionResponse response, @NonNull String secret) throws SignatureException {
        TransactionCreateResponse result = response.getResult();
        if (null == result)
            throw new SignatureException(-1, "The result in response cannot be null");

        String occ = result.getOcc();
        String externalUniqueNumber = result.getExternalUniqueNumber();
        String issuedAtAsString = String.valueOf(result.getIssuedAt());

        if (null == occ || null == externalUniqueNumber)
            throw new SignatureException(-1, "SendTransactionResponse.occ and SendTransactionResponse.externalUniqueNumber cannot be null");

        String data = occ.length() + occ;
        data += externalUniqueNumber.length() + externalUniqueNumber;
        data += issuedAtAsString.length() + issuedAtAsString;
        String sign = base64Encoder.encode(crypt(data, secret));

        return sign.equalsIgnoreCase(result.getSignature());
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
