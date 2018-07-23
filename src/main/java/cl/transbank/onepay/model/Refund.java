package cl.transbank.onepay.model;

import cl.transbank.onepay.Onepay;
import cl.transbank.onepay.exception.RefundCreateException;
import cl.transbank.onepay.exception.SignatureException;
import cl.transbank.onepay.net.Channel;
import cl.transbank.onepay.net.NullifyTransactionRequest;
import cl.transbank.onepay.net.NullifyTransactionResponse;

import java.io.IOException;
import java.net.URL;

public class Refund extends Channel {
    private static final String SERVICE_URI = String.format("%s/ewallet-plugin-api-services/services/transactionservice",
            Onepay.getIntegrationType().getApiBase());
    private static final String CREATE_REFUND = "nullifytransaction";

    public static RefundCreateResponse create(long amount, String occ, String externalUniqueNumber,
                                              String authorizationCode)
            throws SignatureException, IOException, RefundCreateException {
        return create(amount, occ, externalUniqueNumber, authorizationCode, null);
    }

    public static RefundCreateResponse create(long amount, String occ, String externalUniqueNumber,
                                              String authorizationCode, Options options)
            throws IOException, SignatureException, RefundCreateException {
        options = Options.build(options);
        NullifyTransactionRequest request = requestBuilder.buildNullifyTransactionRequest(amount, occ, externalUniqueNumber,
                authorizationCode, options);
        String jsonIn = jsonUtil.jsonEncode(request);
        String jsonOut = request(new URL(String.format("%s/%s", SERVICE_URI, CREATE_REFUND)), RequestMethod.POST, jsonIn);
        NullifyTransactionResponse response = jsonUtil.jsonDecode(jsonOut, NullifyTransactionResponse.class);

        if (null == response || null == response.getResponseCode()) {
            throw new RefundCreateException("Could not obtain the service response");
        } else if (!response.getResponseCode().equalsIgnoreCase("ok")) {
            throw new RefundCreateException(String.format("%s : %s", response.getResponseCode(), response.getDescription()));
        }

        return response.getResult();
    }
}
