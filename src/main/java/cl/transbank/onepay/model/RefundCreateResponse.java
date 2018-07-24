package cl.transbank.onepay.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

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
    public String getHashableString() {
        final String occ = Objects.toString(getOcc(), "");
        final String externalUniqueNumber = Objects.toString(getExternalUniqueNumber(), "");
        final String reverseCode = Objects.toString(getReverseCode(), "");
        final String issuedAtAsString = String.valueOf(getIssuedAt());

        return occ.length() + occ
                + externalUniqueNumber.length() + externalUniqueNumber
                + reverseCode.length() + reverseCode
                + issuedAtAsString.length() + issuedAtAsString;
    }
}
