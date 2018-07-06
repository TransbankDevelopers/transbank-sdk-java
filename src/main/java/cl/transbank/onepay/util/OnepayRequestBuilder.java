package cl.transbank.onepay.util;

import cl.transbank.onepay.Onepay;
import cl.transbank.onepay.exception.SignException;
import cl.transbank.onepay.model.*;
import lombok.NonNull;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.UUID;

public class OnepayRequestBuilder {
    private static OnepayRequestBuilder instance;

    public TransactionCreateRequest build(ShoppingCart cart, Options options) throws SignException {
        options = buildOptions(options);
        TransactionCreateRequest request = new TransactionCreateRequest(UUID.randomUUID().toString(), cart.getTotal(),
                cart.getItemQuantity(), new Date().getTime()/1000, cart.getItems(), Onepay.getCallbackUrl(), "WEB");
        prepareRequest(request, options);
        return OnePaySignUtil.getInstance().sign(request, options.getSharedSecret());
    }

    public TransactionCommitRequest build(String occ, String externalUniqueNumber, Options options) throws SignException {
        options = buildOptions(options);
        TransactionCommitRequest request = new TransactionCommitRequest(occ, externalUniqueNumber, new Date().getTime()/1000);
        prepareRequest(request, options);
        return OnePaySignUtil.getInstance().sign(request, options.getSharedSecret());
    }

    protected Options buildOptions(Options options) {
        if (null == options) return Options.getDefaults();

        if (null == options.getApiKey()) options.setApiKey(Onepay.getApiKey());
        if (null == options.getAppKey()) options.setAppKey(Onepay.getAppKey());
        if (null == options.getSharedSecret()) options.setSharedSecret(Onepay.getSharedSecret());

        return options;
    }

    protected void prepareRequest(@NonNull BaseRequest base, @NonNull Options options) {
        base.setApiKey(options.getApiKey());
        base.setAppKey(options.getAppKey());
    }

    private OnepayRequestBuilder() {
        super();
    }

    public static OnepayRequestBuilder getInstance() {
        if (null == instance) instance = new OnepayRequestBuilder();
        return instance;
    }
}
