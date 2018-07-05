package cl.transbank.onepay.util;

import cl.transbank.onepay.Onepay;
import cl.transbank.onepay.model.Options;
import cl.transbank.onepay.model.ShoppingCart;
import cl.transbank.onepay.model.TransactionCreateRequest;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

public class OnepayRequestBuilder {
    private static OnepayRequestBuilder instance;

    public TransactionCreateRequest build(ShoppingCart cart, Options options) throws NoSuchAlgorithmException, InvalidKeyException {
        options = buildOptions(options);
        TransactionCreateRequest request = new TransactionCreateRequest(new Date().getTime()/1000+10, cart.getTotal(),
                cart.getItemQuantity(), new Date().getTime()/1000, cart.getItems(), Onepay.getCallbackUrl(), "WEB");
        request.setApiKey(options.getApiKey());
        request.setAppKey(options.getAppKey());
        return OnePaySignUtil.getInstance().sign(request, options.getSharedSecret());
    }

    protected static synchronized Options buildOptions(Options options) {
        if (null == options) return Options.getDefaults();

        if (null == options.getApiKey()) options.setApiKey(Onepay.getApiKey());
        if (null == options.getAppKey()) options.setAppKey(Onepay.getAppKey());
        if (null == options.getSharedSecret()) options.setSharedSecret(Onepay.getSharedSecret());

        return options;
    }

    private OnepayRequestBuilder() {
        super();
    }

    public static OnepayRequestBuilder getInstance() {
        if (null == instance) instance = new OnepayRequestBuilder();
        return instance;
    }
}
