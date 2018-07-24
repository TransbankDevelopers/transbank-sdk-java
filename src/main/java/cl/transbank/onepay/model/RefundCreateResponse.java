package cl.transbank.onepay.model;

import cl.transbank.onepay.exception.SignatureException;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class RefundCreateResponse implements Signable {
    private String occ;
    private String externalUniqueNumber;
    private String reverseCode;
    private long issuedAt;
    private String signature;

    @Override
    public String getHashableString() throws SignatureException {
        final String occ = getOcc();
        final String externalUniqueNumber = getExternalUniqueNumber();
        final String reverseCode = getReverseCode();
        final String issuedAtAsString = String.valueOf(getIssuedAt());

        return occ.length() + occ
                + externalUniqueNumber.length() + externalUniqueNumber
                + reverseCode.length() + reverseCode
                + issuedAtAsString.length() + issuedAtAsString;
    }
}
