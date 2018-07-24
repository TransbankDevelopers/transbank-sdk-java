package cl.transbank.onepay.net;

import cl.transbank.onepay.model.Signable;
import lombok.*;

@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class GetTransactionNumberRequest extends BaseRequest
        implements Signable {
    @NonNull private String occ;
    @NonNull private String externalUniqueNumber;
    @NonNull private long issuedAt;
    private String signature;

    @Override
    public String getHashableString() {
        final String occ = getOcc();
        final String externalUniqueNumber = getExternalUniqueNumber();
        final String issuedAtAsString = String.valueOf(getIssuedAt());

        return occ.length() + occ
                + externalUniqueNumber.length() + externalUniqueNumber
                + issuedAtAsString.length() + issuedAtAsString;
    }
}
