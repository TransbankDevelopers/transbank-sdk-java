package cl.transbank.onepay.net;

import cl.transbank.onepay.model.Signable;
import lombok.*;

@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class NullifyTransactionRequest extends BaseRequest
        implements Signable {
    @NonNull private long nullifyAmount;
    @NonNull private String occ;
    @NonNull private String externalUniqueNumber;
    @NonNull private String authorizationCode;
    @NonNull private long issuedAt;
    private String signature;

    @Override
    public String getHashableString() {
        final String occ = getOcc();
        final String externalUniqueNumber = getExternalUniqueNumber();
        final String authorizationCode = getAuthorizationCode();
        final String issuedAtAsString = String.valueOf(getIssuedAt());
        final String nullifyAmountAsString = String.valueOf(getNullifyAmount());

        return occ.length() + occ
                + externalUniqueNumber.length() + externalUniqueNumber
                + authorizationCode.length() + authorizationCode
                + issuedAtAsString.length() + issuedAtAsString
                + nullifyAmountAsString.length() + nullifyAmountAsString;
    }
}
