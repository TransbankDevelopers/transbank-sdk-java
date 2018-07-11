package cl.transbank.onepay.util;

import cl.transbank.onepay.Onepay;
import cl.transbank.onepay.exception.SignException;
import cl.transbank.onepay.model.*;
import lombok.NonNull;

import java.util.Date;
import java.util.UUID;

public class OnepayRequestBuilder {
    private static OnepayRequestBuilder instance;

    public TransactionCreateRequest build(ShoppingCart cart, Options options, Class<TransactionCreateRequest> clazz)
            throws SignException {
        options = buildOptions(options);
        //TransactionCreateRequest request = new TransactionCreateRequest(UUID.randomUUID().toString(), cart.getTotal(),
        TransactionCreateRequest request = new TransactionCreateRequest(String.valueOf(new Date().getTime()), cart.getTotal(), // apparently the externalUniqueCode should be numeric!!
                cart.getItemQuantity(), new Date().getTime()/1000, cart.getItems(), Onepay.getCallbackUrl(),
                "WEB");
        prepareRequest(request, options);
        return OnePaySignUtil.getInstance().sign(request, options.getSharedSecret());
    }

    public TransactionCommitRequest build(String occ, String externalUniqueNumber, Options options,
                                          Class<TransactionCommitRequest> clazz) throws SignException {
        options = buildOptions(options);
        TransactionCommitRequest request = new TransactionCommitRequest(occ, externalUniqueNumber,
                new Date().getTime()/1000);
        prepareRequest(request, options);
        return OnePaySignUtil.getInstance().sign(request, options.getSharedSecret());
    }

    public RefundCreateRequest build(long amount, String occ, String externalUniqueNumber,
                                     String authorizationCode, Options options, Class<RefundCreateRequest> clazz)
        throws SignException {
        options = buildOptions(options);
        RefundCreateRequest request = new RefundCreateRequest(amount, occ, externalUniqueNumber, authorizationCode,
                new Date().getTime()/1000);
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
