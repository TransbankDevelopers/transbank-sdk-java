package cl.transbank.onepay.model;

import cl.transbank.onepay.Onepay;
import cl.transbank.onepay.exception.RefundCreateException;
import cl.transbank.onepay.exception.SignException;
import cl.transbank.onepay.net.Channel;
import cl.transbank.onepay.net.NullifyTransactionRequest;
import cl.transbank.onepay.net.NullifyTransactionResponse;
import cl.transbank.onepay.util.JsonUtil;
import cl.transbank.onepay.util.OnepayRequestBuilder;

import java.io.IOException;
import java.net.URL;

public class Refund extends Channel {
    private static final String SERVICE_URI = String.format("%s/ewallet-plugin-api-services/services/transactionservice",
            Onepay.getIntegrationType().getApiBase());
    private static final String CREATE_REFUND = "nullifytransaction";

    public static RefundCreateResponse create(long amount, String occ, String externalUniqueNumber,
                                              String authorizationCode)
            throws SignException, IOException, RefundCreateException {
        return create(amount, occ, externalUniqueNumber, authorizationCode, null);
    }

    public static RefundCreateResponse create(long amount, String occ, String externalUniqueNumber,
                                              String authorizationCode, Options options)
            throws SignException, IOException, RefundCreateException {
        NullifyTransactionRequest request = OnepayRequestBuilder.getInstance().build(amount, occ, externalUniqueNumber, authorizationCode, options,
                NullifyTransactionRequest.class);
        String jsonIn = JsonUtil.getInstance().jsonEncode(request);
        String jsonOut = request(new URL(String.format("%s/%s", SERVICE_URI, CREATE_REFUND)), RequestMethod.POST, jsonIn);
        NullifyTransactionResponse response = JsonUtil.getInstance().jsonDecode(jsonOut, NullifyTransactionResponse.class);

        if (null == response) {
            throw new RefundCreateException(-1, "Could not obtain the service response");
        } else if (!response.getResponseCode().equalsIgnoreCase("ok")) {
            throw new RefundCreateException(-1, String.format("%s : %s", response.getResponseCode(), response.getDescription()));
        }

        return response.getResult();
    }
}
