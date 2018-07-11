package cl.transbank.onepay.model;

import cl.transbank.onepay.Onepay;
import cl.transbank.onepay.exception.SignException;
import cl.transbank.onepay.exception.TransactionCreateException;
import cl.transbank.onepay.net.Channel;
import cl.transbank.onepay.net.SendTransactionRequest;
import cl.transbank.onepay.net.SendTransactionResponse;
import cl.transbank.onepay.util.JsonUtil;
import cl.transbank.onepay.util.OnepayRequestBuilder;
import lombok.NonNull;

import java.io.IOException;
import java.net.URL;

public class Transaction extends Channel {
    private static final String SERVICE_URI = String.format("%s/ewallet-plugin-api-services/services/transactionservice",
            Onepay.getIntegrationType().getApiBase());
    private static final String SEND_TRANSACTION = "sendtransaction";
    private static final String COMMIT_TRANSACTION = "gettransactionnumber";

    public static TransactionCreateResponse create(@NonNull ShoppingCart cart)
            throws IOException, SignException, TransactionCreateException {
        return create(cart, null);
    }

    public static TransactionCreateResponse create(@NonNull ShoppingCart cart, Options options)
            throws IOException, SignException, TransactionCreateException {
        SendTransactionRequest request = OnepayRequestBuilder.getInstance().build(cart, options);
        String jsonIn = JsonUtil.getInstance().jsonEncode(request);
        String jsonOut = request(new URL(String.format("%s/%s", SERVICE_URI, SEND_TRANSACTION)), RequestMethod.POST, jsonIn);
        SendTransactionResponse response = JsonUtil.getInstance().jsonDecode(jsonOut, SendTransactionResponse.class);

        if (null == response) {
            throw new TransactionCreateException(-1, "Could not obtain the service response");
        } else if (!response.getResponseCode().equalsIgnoreCase("ok")) {
            throw new TransactionCreateException(-1, String.format("%s : %s", response.getResponseCode(), response.getDescription()));
        }

        return response.getResult();
    }

    public static TransactionCommitResponse commit(String occ, String externalUniqueNumber)
            throws IOException, SignException {
        return commit(occ, externalUniqueNumber, null);
    }

    public static TransactionCommitResponse commit(String occ, String externalUniqueNumber, Options options)
            throws IOException, SignException {
        TransactionCommitRequest request = OnepayRequestBuilder.getInstance().build(occ, externalUniqueNumber, options);
        String jsonIn = JsonUtil.getInstance().jsonEncode(request);
        String jsonOut = request(new URL(String.format("%s/%s", SERVICE_URI, COMMIT_TRANSACTION)), RequestMethod.POST, jsonIn);
        TransactionCommitResponse response = JsonUtil.getInstance().jsonDecode(jsonOut, TransactionCommitResponse.class);
        return response;
    }
}
