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

        String data = occ.length() + occ;
        data += externalUniqueNumber.length() + externalUniqueNumber;
        data += authorizationCode.length() + authorizationCode;
        data += issuedAtAsString.length() + issuedAtAsString;
        data += nullifyAmountAsString.length() + nullifyAmountAsString;

        return data;
    }
}
