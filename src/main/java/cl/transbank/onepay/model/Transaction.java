package cl.transbank.onepay.model;

import cl.transbank.onepay.Onepay;
import cl.transbank.onepay.net.Channel;
import cl.transbank.onepay.util.JsonUtil;
import lombok.NonNull;

import java.io.IOException;
import java.net.URL;

public class Transaction extends Channel {
    private static final String SERVICE_URI = String.format("%s/ewallet-plugin-api-services/services/transactionservice", Onepay.integrationType.getApiBase());
    public static final String SEND_TRANSACTION = "sendtransaction";

    public static CreateTransactionResponse create(@NonNull ShoppingCart cart) throws IOException {
        CreateTransactionRequest request = CreateTransactionRequest.build(cart);
        String jsonIn = JsonUtil.getInstance().jsonEncode(request);
        String jsonOut = request(new URL(String.format("%s/%s", SERVICE_URI, SEND_TRANSACTION)), RequestMethod.POST, jsonIn);
        CreateTransactionResponse response = JsonUtil.getInstance().jsonDecode(jsonOut, CreateTransactionResponse.class);
        return response;
    }
}
