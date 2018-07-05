package cl.transbank.onepay.model;

import cl.transbank.onepay.Onepay;
import cl.transbank.onepay.net.Channel;
import cl.transbank.onepay.util.JsonUtil;
import cl.transbank.onepay.util.OnepayRequestBuilder;
import lombok.NonNull;

import java.io.IOException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class Transaction extends Channel {
    private static final String SERVICE_URI = String.format("%s/ewallet-plugin-api-services/services/transactionservice", Onepay.getIntegrationType().getApiBase());
    public static final String SEND_TRANSACTION = "sendtransaction";

    public static TransactionCreateResponse create(@NonNull ShoppingCart cart) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        return create(cart, null);
    }

    public static TransactionCreateResponse create(@NonNull ShoppingCart cart, Options options) throws IOException, InvalidKeyException, NoSuchAlgorithmException {
        TransactionCreateRequest request = OnepayRequestBuilder.getInstance().build(cart, options);
        String jsonIn = JsonUtil.getInstance().jsonEncode(request);
        String jsonOut = request(new URL(String.format("%s/%s", SERVICE_URI, SEND_TRANSACTION)), RequestMethod.POST, jsonIn);
        TransactionCreateResponse response = JsonUtil.getInstance().jsonDecode(jsonOut, TransactionCreateResponse.class);
        return response;
    }
}
