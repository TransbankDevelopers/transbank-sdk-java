package cl.transbank.onepay.util;

import cl.transbank.onepay.Onepay;
import cl.transbank.onepay.exception.SignatureException;
import cl.transbank.onepay.model.*;
import cl.transbank.onepay.net.BaseRequest;
import cl.transbank.onepay.net.NullifyTransactionRequest;
import cl.transbank.onepay.net.SendTransactionRequest;
import cl.transbank.onepay.net.GetTransactionNumberRequest;
import lombok.NonNull;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

public class OnepayRequestBuilder implements RequestBuilder {
    private static OnepayRequestBuilder instance;
    private static OnepaySignUtil onePaySignUtil;

    public SendTransactionRequest buildSendTransactionRequest(ShoppingCart cart, Onepay.Channel channel, @NonNull String externalUniqueNumber, Options options)
            throws SignatureException {
        String callbackUrl = Onepay.getCallbackUrl() == null || Onepay.getCallbackUrl().isEmpty() ?
                Onepay.DEFAULT_CALLBACK : Onepay.getCallbackUrl();

        SendTransactionRequest request = new SendTransactionRequest(externalUniqueNumber, cart.getTotal(),
                cart.getItemsQuantity(), new Date().getTime()/1000, cart.getItems(), callbackUrl,
                channel.toString(), Objects.toString(Onepay.getAppScheme(), ""));
        prepareRequest(request, options);
        onePaySignUtil.sign(request, options.getSharedSecret());
        return request;
    }

    public GetTransactionNumberRequest buildGetTransactionNumberRequest(String occ, String externalUniqueNumber,
                                                                        Options options) throws SignatureException {
        GetTransactionNumberRequest request = new GetTransactionNumberRequest(occ, externalUniqueNumber,
                new Date().getTime()/1000);
        prepareRequest(request, options);
        onePaySignUtil.sign(request, options.getSharedSecret());
        return request;
    }

    public NullifyTransactionRequest buildNullifyTransactionRequest(long amount, String occ, String externalUniqueNumber,
                                           String authorizationCode, Options options)
            throws SignatureException {
        NullifyTransactionRequest request = new NullifyTransactionRequest(amount, occ, externalUniqueNumber, authorizationCode,
                new Date().getTime()/1000);
        prepareRequest(request, options);
        onePaySignUtil.sign(request, options.getSharedSecret());
        return request;
    }

    private void prepareRequest(@NonNull BaseRequest base, @NonNull Options options) {
        base.setApiKey(options.getApiKey());
        base.setAppKey(Onepay.APP_KEY);
    }

    private OnepayRequestBuilder() {
        super();
        onePaySignUtil = OnepaySignUtil.getInstance();
    }

    public static OnepayRequestBuilder getInstance() {
        if (null == instance) {
            instance = new OnepayRequestBuilder();
        }
        return instance;
    }
}
