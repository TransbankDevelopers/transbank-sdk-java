package cl.transbank.onepay.model;

import cl.transbank.onepay.exception.SignatureException;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class TransactionCreateResponse implements Signable {
    private String occ;
    private long ott;
    private String externalUniqueNumber;
    private String qrCodeAsBase64;
    private long issuedAt;
    private String signature;

    @Override
    public String getHash() throws SignatureException {
        String occ = getOcc();
        String externalUniqueNumber = getExternalUniqueNumber();
        String issuedAtAsString = String.valueOf(getIssuedAt());

        if (null == occ || null == externalUniqueNumber)
            throw new SignatureException(-1, "SendTransactionResponse.occ and SendTransactionResponse.externalUniqueNumber cannot be null");

        String data = occ.length() + occ;
        data += externalUniqueNumber.length() + externalUniqueNumber;
        data += issuedAtAsString.length() + issuedAtAsString;

        return data;
    }
}
