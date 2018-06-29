package cl.transbank.onepay.model;

import cl.transbank.onepay.Onepay;
import cl.transbank.onepay.util.Signable;
import cl.transbank.onepay.util.OnePaySignUtil;
import lombok.*;
import sun.misc.BASE64Encoder;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

@Getter
@Setter(AccessLevel.PROTECTED)
@ToString
public final class CreateTransactionRequest extends BaseRequest implements Signable<CreateTransactionRequest> {
    private long externalUniqueNumber;
    private long total;
    private int itemsQuantity;
    private long issuedAt;
    @NonNull private List<Item> items;
    @NonNull private String callbackUrl;
    @NonNull private String signature;
    private String channel;

    private CreateTransactionRequest() {
        super();
    }

    public static CreateTransactionRequest build(@NonNull ShoppingCart cart) {
        CreateTransactionRequest request = new CreateTransactionRequest();
        request.setExternalUniqueNumber(new Date().getTime()/1000+10);
        request.setCallbackUrl(Onepay.callbackUrl);
        request.setApiKey(Onepay.apiKey);
        request.setAppKey(Onepay.appKey);
        request.setItems(cart.getItems());
        request.setTotal(cart.getTotal());
        request.setItemsQuantity(cart.getItemQuantity());
        request.setIssuedAt(new Date().getTime()/1000);
        return request;
    }

    public CreateTransactionRequest sign() throws NoSuchAlgorithmException, InvalidKeyException {
        String externalUniqueNumberAsString = String.valueOf(getExternalUniqueNumber());
        String totalAsString = String.valueOf(getTotal());
        String itemsQuantityAsString = String.valueOf(getItemsQuantity());
        String issuedAtAsString = String.valueOf(getIssuedAt());

        String data = externalUniqueNumberAsString.length() + externalUniqueNumberAsString;
        data += totalAsString.length() + totalAsString;
        data += itemsQuantityAsString.length() + itemsQuantityAsString;
        data += issuedAtAsString.length() + issuedAtAsString;
        data += getCallbackUrl().length() + getCallbackUrl();

        byte[] sign = OnePaySignUtil.getInstance().sign(data, Onepay.sharedSecret);

        BASE64Encoder encoder = new BASE64Encoder();
        setSignature(encoder.encode(sign));

        return this;
    }
}
