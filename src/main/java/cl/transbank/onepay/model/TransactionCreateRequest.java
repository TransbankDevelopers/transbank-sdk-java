package cl.transbank.onepay.model;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@ToString
public final class TransactionCreateRequest extends BaseRequest {
    @NonNull private String externalUniqueNumber;
    @NonNull private long total;
    @NonNull private int itemsQuantity;
    @NonNull private long issuedAt;
    @NonNull private List<Item> items;
    @NonNull private String callbackUrl;
    @NonNull private String channel;
    private String signature;
    private final boolean generateOttQrCode = true;
}
