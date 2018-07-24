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
        String occ = getOcc();
        String externalUniqueNumber = getExternalUniqueNumber();
        String issuedAtAsString = String.valueOf(getIssuedAt());

        return occ.length() + occ
                + externalUniqueNumber.length() + externalUniqueNumber
                + issuedAtAsString.length() + issuedAtAsString;
    }
}
