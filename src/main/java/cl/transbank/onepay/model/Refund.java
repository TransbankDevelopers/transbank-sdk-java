package cl.transbank.onepay.model;

import cl.transbank.onepay.ApiBaseResource;
import cl.transbank.onepay.Onepay;
import cl.transbank.onepay.exception.RefundCreateException;
import cl.transbank.onepay.exception.SignatureException;
import cl.transbank.onepay.net.NullifyTransactionRequest;
import cl.transbank.onepay.net.NullifyTransactionResponse;
import cl.transbank.util.HttpUtil;
import cl.transbank.webpay.exception.TransbankHttpApiException;

import java.io.IOException;
import java.net.URL;

public class Refund extends ApiBaseResource {
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
        NullifyTransactionRequest request = getRequestBuilder().buildNullifyTransactionRequest(amount, occ, externalUniqueNumber,
                authorizationCode, options);
        String jsonIn = getJsonUtil().jsonEncode(request);

        String jsonOut = null;
        try {
            jsonOut = request(
                    new URL(String.format("%s/%s", Onepay.getCurrentIntegrationTypeUrl(), CREATE_REFUND)),
                    HttpUtil.RequestMethod.POST, jsonIn);
        } catch (TransbankHttpApiException e) {
            throw new RefundCreateException(e);
        }

        NullifyTransactionResponse response = getJsonUtil().jsonDecode(jsonOut, NullifyTransactionResponse.class);

        if (null == response || null == response.getResponseCode()) {
            throw new RefundCreateException("Could not obtain the service response");
        } else if (!response.getResponseCode().equalsIgnoreCase("ok")) {
            throw new RefundCreateException(String.format("%s : %s", response.getResponseCode(), response.getDescription()));
        }

        if (!getSignUtil().validate(response.getResult(), options.getSharedSecret()))
            throw new SignatureException("The response signature is not valid");

        return response.getResult();
    }
}
