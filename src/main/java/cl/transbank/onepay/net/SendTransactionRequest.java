package cl.transbank.onepay.net;

import cl.transbank.onepay.model.Item;
import cl.transbank.onepay.model.Signable;
import lombok.*;

import java.util.List;

@RequiredArgsConstructor
@Getter
@Setter
@ToString
public final class SendTransactionRequest extends BaseRequest
        implements Signable {
    @NonNull private final String externalUniqueNumber;
    @NonNull private final Long total;
    @NonNull private final Integer itemsQuantity;
    @NonNull private final Long issuedAt;
    @NonNull private final List<Item> items;
    @NonNull private final String callbackUrl;
    @NonNull private final String channel;
    @NonNull private final String appScheme;
    private final Integer widthHeight;
    private final String commerceLogoUrl;
    private String signature;
    private final boolean generateOttQrCode = true;

    @Override
    public String getHashableString() {
        final String externalUniqueNumberAsString = getExternalUniqueNumber();
        final String totalAsString = String.valueOf(getTotal());
        final String itemsQuantityAsString = String.valueOf(getItemsQuantity());
        final String issuedAtAsString = String.valueOf(getIssuedAt());
        final String callbackUrlAsString = getCallbackUrl();

        return externalUniqueNumberAsString.length() + externalUniqueNumberAsString
                + totalAsString.length() + totalAsString
                + itemsQuantityAsString.length() + itemsQuantityAsString
                + issuedAtAsString.length() + issuedAtAsString
                + callbackUrlAsString.length() + callbackUrlAsString;
    }
}
