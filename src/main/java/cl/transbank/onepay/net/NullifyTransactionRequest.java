package cl.transbank.onepay.net;

import cl.transbank.onepay.model.Signable;
import lombok.*;

import java.util.Objects;

@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class NullifyTransactionRequest extends BaseRequest
        implements Signable {
    @NonNull private Long nullifyAmount;
    @NonNull private String occ;
    @NonNull private String externalUniqueNumber;
    @NonNull private String authorizationCode;
    @NonNull private Long issuedAt;
    private String signature;

    @Override
    public String getHashableString() {
        final String occ = Objects.toString(getOcc(), "");
        final String externalUniqueNumber = Objects.toString(getExternalUniqueNumber(), "");
        final String authorizationCode = Objects.toString(getAuthorizationCode(), "");
        final String issuedAtAsString = String.valueOf(getIssuedAt());
        final String nullifyAmountAsString = String.valueOf(getNullifyAmount());

        return occ.length() + occ
                + externalUniqueNumber.length() + externalUniqueNumber
                + authorizationCode.length() + authorizationCode
                + issuedAtAsString.length() + issuedAtAsString
                + nullifyAmountAsString.length() + nullifyAmountAsString;
    }
}
