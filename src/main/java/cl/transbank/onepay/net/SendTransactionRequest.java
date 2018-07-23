package cl.transbank.onepay.net;

import cl.transbank.onepay.Onepay;
import cl.transbank.onepay.model.Item;
import cl.transbank.onepay.model.Signable;
import lombok.*;

import java.util.List;

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
    public String getHash() {
        String externalUniqueNumberAsString = String.valueOf(getExternalUniqueNumber());
        String totalAsString = String.valueOf(getTotal());
        String itemsQuantityAsString = String.valueOf(getItemsQuantity());
        String issuedAtAsString = String.valueOf(getIssuedAt());

        String data = externalUniqueNumberAsString.length() + externalUniqueNumberAsString;
        data += totalAsString.length() + totalAsString;
        data += itemsQuantityAsString.length() + itemsQuantityAsString;
        data += issuedAtAsString.length() + issuedAtAsString;
        data += Onepay.FAKE_CALLBACK_URL.length() + Onepay.FAKE_CALLBACK_URL;

        return data;
    }
}
