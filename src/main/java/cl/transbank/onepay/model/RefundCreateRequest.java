package cl.transbank.onepay.model;

import lombok.*;

@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class RefundCreateRequest extends BaseRequest {
    @NonNull private long nullifyAmount;
    @NonNull private String occ;
    @NonNull private String externalUniqueNumber;
    @NonNull private String authorizationCode;
    @NonNull private long issuedAt;
    private String signature;
}
