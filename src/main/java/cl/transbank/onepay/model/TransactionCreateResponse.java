package cl.transbank.onepay.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

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
    public String getHashableString() {
        final String occ = Objects.toString(getOcc(), "");
        final String externalUniqueNumber = Objects.toString(getExternalUniqueNumber(), "");
        final String issuedAtAsString = String.valueOf(getIssuedAt());

        return occ.length() + occ
                + externalUniqueNumber.length() + externalUniqueNumber
                + issuedAtAsString.length() + issuedAtAsString;
    }
}
