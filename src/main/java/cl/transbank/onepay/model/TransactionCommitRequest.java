package cl.transbank.onepay.model;

import lombok.*;

@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class TransactionCommitRequest extends BaseRequest {
    @NonNull private String occ;
    @NonNull private String externalUniqueNumber;
    @NonNull private long issuedAt;
    private String signature;
}
