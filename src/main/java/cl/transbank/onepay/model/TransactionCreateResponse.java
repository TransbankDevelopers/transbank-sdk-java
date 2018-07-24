package cl.transbank.onepay.model;

import cl.transbank.onepay.exception.SignatureException;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TransactionCreateResponse implements Signable {
    private String occ;
    private long ott;
    private String externalUniqueNumber;
    private String qrCodeAsBase64;
    private long issuedAt;
    private String signature;

    @Override
    public String getHashableString() throws SignatureException {
        final String occ = getOcc();
        final String externalUniqueNumber = getExternalUniqueNumber();
        final String issuedAtAsString = String.valueOf(getIssuedAt());

        if (null == occ || null == externalUniqueNumber)
            throw new SignatureException("SendTransactionResponse.occ and SendTransactionResponse.externalUniqueNumber cannot be null");

        return occ.length() + occ
                + externalUniqueNumber.length() + externalUniqueNumber
                + issuedAtAsString.length() + issuedAtAsString;
    }
}
