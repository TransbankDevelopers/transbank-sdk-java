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
        String occ = getOcc();
        String externalUniqueNumber = getExternalUniqueNumber();
        String authorizationCode = getAuthorizationCode();
        String issuedAtAsString = String.valueOf(getIssuedAt());
        String nullifyAmountAsString = String.valueOf(getNullifyAmount());

        return occ.length() + occ
                + externalUniqueNumber.length() + externalUniqueNumber
                + authorizationCode.length() + authorizationCode
                + issuedAtAsString.length() + issuedAtAsString
                + nullifyAmountAsString.length() + nullifyAmountAsString;
    }
}
