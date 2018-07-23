package cl.transbank.onepay.model;

import cl.transbank.onepay.Onepay;
import cl.transbank.onepay.exception.SignatureException;
import cl.transbank.onepay.exception.TransactionCommitException;
import cl.transbank.onepay.exception.TransactionCreateException;
import cl.transbank.onepay.net.*;
import cl.transbank.onepay.util.JsonUtil;
import cl.transbank.onepay.util.OnePaySignUtil;
import cl.transbank.onepay.util.OnepayRequestBuilder;
import lombok.NonNull;

import java.io.IOException;
import java.net.URL;

public class Transaction extends Channel {
    private static final String SERVICE_URI = String.format("%s/ewallet-plugin-api-services/services/transactionservice",
            Onepay.getIntegrationType().getApiBase());
    private static final String SEND_TRANSACTION = "sendtransaction";
    private static final String COMMIT_TRANSACTION = "gettransactionnumber";
    private static OnePaySignUtil signUtil = OnePaySignUtil.getInstance();

    public static TransactionCreateResponse create(@NonNull ShoppingCart cart)
            throws IOException, SignatureException, TransactionCreateException {
        return create(cart, null);
    }

    public static TransactionCreateResponse create(@NonNull ShoppingCart cart, Options options)
            throws IOException, SignatureException, TransactionCreateException {
        options = Options.build(options);
        SendTransactionRequest request = requestBuilder.build(cart, options, SendTransactionRequest.class);
        String jsonIn = jsonUtil.jsonEncode(request);
        String jsonOut = request(new URL(String.format("%s/%s", SERVICE_URI, SEND_TRANSACTION)), RequestMethod.POST, jsonIn);
        SendTransactionResponse response = jsonUtil.jsonDecode(jsonOut, SendTransactionResponse.class);

        if (null == response) {
            throw new TransactionCreateException("Could not obtain the service response");
        } else if (!response.getResponseCode().equalsIgnoreCase("ok")) {
            throw new TransactionCreateException(String.format("%s : %s", response.getResponseCode(), response.getDescription()));
        }

        if (!signUtil.validate(response.getResult(), options.getSharedSecret()))
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
        GetTransactionNumberRequest request = requestBuilder.build(occ, externalUniqueNumber, options, GetTransactionNumberRequest.class);
        String jsonIn = jsonUtil.jsonEncode(request);
        String jsonOut = request(new URL(String.format("%s/%s", SERVICE_URI, COMMIT_TRANSACTION)), RequestMethod.POST, jsonIn);
        GetTransactionNumberResponse response = jsonUtil.jsonDecode(jsonOut, GetTransactionNumberResponse.class);

        if (null == response) {
            throw new TransactionCommitException("Could not obtain the service response");
        } else if (!response.getResponseCode().equalsIgnoreCase("ok")) {
            throw new TransactionCommitException(String.format("%s : %s", response.getResponseCode(), response.getDescription()));
        }

        return response.getResult();
    }
}
