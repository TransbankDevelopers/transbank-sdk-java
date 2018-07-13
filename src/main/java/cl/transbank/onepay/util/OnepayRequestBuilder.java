package cl.transbank.onepay.util;

import cl.transbank.onepay.Onepay;
import cl.transbank.onepay.exception.SignatureException;
import cl.transbank.onepay.model.*;
import cl.transbank.onepay.net.BaseRequest;
import cl.transbank.onepay.net.SendTransactionRequest;
import cl.transbank.onepay.net.GetTransactionNumberRequest;
import lombok.NonNull;

import java.util.Date;
import java.util.UUID;

public class OnepayRequestBuilder {
    private static OnepayRequestBuilder instance;

    public SendTransactionRequest build(ShoppingCart cart, Options options) throws SignatureException {
        SendTransactionRequest request = new SendTransactionRequest(UUID.randomUUID().toString(), cart.getTotal(),
                cart.getItemQuantity(), new Date().getTime()/1000, cart.getItems(), Onepay.FAKE_CALLBACK_URL, "WEB");
        prepareRequest(request, options);
        return OnePaySignUtil.getInstance().sign(request, options.getSharedSecret());
    }

    public GetTransactionNumberRequest build(String occ, String externalUniqueNumber, Options options) throws SignatureException {
        GetTransactionNumberRequest request = new GetTransactionNumberRequest(occ, externalUniqueNumber, new Date().getTime()/1000);
        prepareRequest(request, options);
        return OnePaySignUtil.getInstance().sign(request, options.getSharedSecret());
    }

    protected void prepareRequest(@NonNull BaseRequest base, @NonNull Options options) {
        base.setApiKey(options.getApiKey());
        base.setAppKey(Onepay.APP_KEY);
    }

    private OnepayRequestBuilder() {
        super();
    }

    public static OnepayRequestBuilder getInstance() {
        if (null == instance) instance = new OnepayRequestBuilder();
        return instance;
    }
}
