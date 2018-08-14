package cl.transbank.onepay.model;

import cl.transbank.onepay.ApiBaseResource;
import cl.transbank.onepay.Onepay;
import cl.transbank.onepay.exception.SignatureException;
import cl.transbank.onepay.exception.TransactionCommitException;
import cl.transbank.onepay.exception.TransactionCreateException;
import cl.transbank.onepay.net.*;
import cl.transbank.onepay.util.HttpUtil;
import lombok.NonNull;

import java.io.IOException;
import java.net.URL;

public class Transaction extends ApiBaseResource {
    private static final String SERVICE_URI = String.format("%s/ewallet-plugin-api-services/services/transactionservice",
            Onepay.getIntegrationType().getApiBase());
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
        return create(cart, channel, null);
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

    public static TransactionCreateResponse create(@NonNull ShoppingCart cart, @NonNull Onepay.Channel channel, Options options)
            throws IOException, SignatureException, TransactionCreateException {
        options = Options.build(options);
        SendTransactionRequest request = getRequestBuilder().buildSendTransactionRequest(cart, channel, options);
        String jsonIn = getJsonUtil().jsonEncode(request);
        System.out.println(jsonIn);
        String jsonOut = request(new URL(String.format("%s/%s", SERVICE_URI, SEND_TRANSACTION)), HttpUtil.RequestMethod.POST, jsonIn);
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
        String jsonOut = request(new URL(String.format("%s/%s", SERVICE_URI, COMMIT_TRANSACTION)), HttpUtil.RequestMethod.POST, jsonIn);
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
