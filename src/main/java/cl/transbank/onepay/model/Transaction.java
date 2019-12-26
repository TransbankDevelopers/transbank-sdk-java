package cl.transbank.onepay.model;

import cl.transbank.onepay.ApiBaseResource;
import cl.transbank.onepay.Onepay;
import cl.transbank.onepay.exception.SignatureException;
import cl.transbank.onepay.exception.TransactionCommitException;
import cl.transbank.onepay.exception.TransactionCreateException;
import cl.transbank.onepay.net.*;
import cl.transbank.util.HttpUtil;
import cl.transbank.webpay.exception.TransbankHttpApiException;
import lombok.NonNull;

import java.io.IOException;
import java.net.URL;
import java.util.UUID;

public class Transaction extends ApiBaseResource {
    private static final String SEND_TRANSACTION = "sendtransaction";
    private static final String COMMIT_TRANSACTION = "gettransactionnumber";

    /**
     *
     * @param cart
     * @return
     * @throws IOException
     * @throws SignatureException
     * @throws TransactionCreateException
     *
     * @deprecated use {@link #create(ShoppingCart, Onepay.Channel)} instead
     */
    @Deprecated
    public static TransactionCreateResponse create(@NonNull ShoppingCart cart)
            throws IOException, SignatureException, TransactionCreateException {
        return create(cart, Onepay.DEFAULT_CHANNEL);
    }

    public static TransactionCreateResponse create(@NonNull ShoppingCart cart, @NonNull Onepay.Channel channel)
            throws IOException, SignatureException, TransactionCreateException {
        return create(cart, channel, (Options) null);
    }

    public static  TransactionCreateResponse create(@NonNull ShoppingCart cart, @NonNull Onepay.Channel channel, String externalUniqueNumber)
            throws IOException, SignatureException, TransactionCreateException{
        return create(cart, channel, externalUniqueNumber, null);
    }

    /**
     *
     * @param cart
     * @param options
     * @return
     * @throws IOException
     * @throws SignatureException
     * @throws TransactionCreateException
     *
     * @deprecated use {@link #create(ShoppingCart, Onepay.Channel, Options)} instead
     */
    @Deprecated
    public static TransactionCreateResponse create(@NonNull ShoppingCart cart, Options options)
            throws IOException, SignatureException, TransactionCreateException {
        return create(cart, Onepay.DEFAULT_CHANNEL, options);
    }

    public static  TransactionCreateResponse create(@NonNull ShoppingCart cart, @NonNull Onepay.Channel channel, Options options)
            throws  IOException, SignatureException, TransactionCreateException{
        return create(cart, channel, UUID.randomUUID().toString() , options);
    }


    public static TransactionCreateResponse create(@NonNull ShoppingCart cart, @NonNull Onepay.Channel channel, @NonNull String externalUniqueNumber, Options options)
            throws IOException, SignatureException, TransactionCreateException {
        if (channel == Onepay.Channel.APP && (Onepay.getAppScheme() == null || Onepay.getAppScheme().isEmpty()))
            throw new TransactionCreateException("You need to set an appScheme if you want to use APP channel");

        if (channel == Onepay.Channel.MOBILE && (Onepay.getCallbackUrl() == null || Onepay.getCallbackUrl().isEmpty()))
            throw new TransactionCreateException("You need to set a valid callback is you want to use MOBILE channel");

        options = Options.build(options);
        SendTransactionRequest request = getRequestBuilder().buildSendTransactionRequest(cart, channel, externalUniqueNumber, options);
        String jsonIn = getJsonUtil().jsonEncode(request);

        String jsonOut = null;
        try {
            jsonOut = request(
                    new URL(String.format("%s/%s", Onepay.getCurrentIntegrationTypeUrl(), SEND_TRANSACTION)),
                    HttpUtil.RequestMethod.POST, jsonIn);
        } catch (TransbankHttpApiException e) {
            throw new TransactionCreateException(e);
        }

        SendTransactionResponse response = getJsonUtil().jsonDecode(jsonOut, SendTransactionResponse.class);

        if (null == response) {
            throw new TransactionCreateException("Could not obtain the service response");
        } else if (!response.getResponseCode().equalsIgnoreCase("ok")) {
            throw new TransactionCreateException(String.format("%s : %s", response.getResponseCode(), response.getDescription()));
        }

        if (!getSignUtil().validate(response.getResult(), options.getSharedSecret()))
            throw new SignatureException("The response signature is not valid");

        return response.getResult();
    }

    public static TransactionCommitResponse commit(String occ, String externalUniqueNumber)
            throws IOException, SignatureException, TransactionCommitException {
        return commit(occ, externalUniqueNumber, null);
    }

    public static TransactionCommitResponse commit(String occ, String externalUniqueNumber, Options options)
            throws IOException, SignatureException, TransactionCommitException {
        options = Options.build(options);
        GetTransactionNumberRequest request = getRequestBuilder().buildGetTransactionNumberRequest(occ, externalUniqueNumber, options);
        String jsonIn = getJsonUtil().jsonEncode(request);

        String jsonOut = null;
        try {
            jsonOut = request(
                    new URL(String.format("%s/%s", Onepay.getCurrentIntegrationTypeUrl(), COMMIT_TRANSACTION)),
                    HttpUtil.RequestMethod.POST, jsonIn);
        } catch (TransbankHttpApiException e) {
            throw new TransactionCommitException(e);
        }

        GetTransactionNumberResponse response = getJsonUtil().jsonDecode(jsonOut, GetTransactionNumberResponse.class);

        if (null == response) {
            throw new TransactionCommitException("Could not obtain the service response");
        } else if (!response.getResponseCode().equalsIgnoreCase("ok")) {
            throw new TransactionCommitException(String.format("%s : %s", response.getResponseCode(), response.getDescription()));
        }

        if (!getSignUtil().validate(response.getResult(), options.getSharedSecret()))
            throw new SignatureException("The response signature is not valid");

        return response.getResult();
    }
}
