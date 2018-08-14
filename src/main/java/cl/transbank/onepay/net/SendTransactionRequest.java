package cl.transbank.onepay.net;

import cl.transbank.onepay.model.Item;
import cl.transbank.onepay.model.Signable;
import lombok.*;

import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@ToString
public final class SendTransactionRequest extends BaseRequest
        implements Signable {
    @NonNull private String externalUniqueNumber;
    @NonNull private long total;
    @NonNull private int itemsQuantity;
    @NonNull private long issuedAt;
    @NonNull private List<Item> items;
    @NonNull private String callbackUrl;
    @NonNull private String channel;
    private String signature;
    private final boolean generateOttQrCode = true;

    @Override
    public String getHashableString() {
        final String externalUniqueNumberAsString = Objects.toString(getExternalUniqueNumber(), "");
        final String totalAsString = String.valueOf(getTotal());
        final String itemsQuantityAsString = String.valueOf(getItemsQuantity());
        final String issuedAtAsString = String.valueOf(getIssuedAt());
        final String callbackUrlAsString = Objects.toString(getCallbackUrl(), "");

        return externalUniqueNumberAsString.length() + externalUniqueNumberAsString
                + totalAsString.length() + totalAsString
                + itemsQuantityAsString.length() + itemsQuantityAsString
                + issuedAtAsString.length() + issuedAtAsString
                + callbackUrlAsString.length() + callbackUrlAsString;
    }
}
