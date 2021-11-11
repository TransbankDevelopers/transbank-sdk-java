package cl.transbank.patpass;

import cl.transbank.common.ApiConstants;
import cl.transbank.common.BaseTransaction;
import cl.transbank.exception.TransbankException;
import cl.transbank.model.WebpayApiRequest;
import cl.transbank.patpass.responses.PatpassComercioTransactionStatusResponse;
import cl.transbank.patpass.requests.PatpassComercioTransactionStatusRequest;
import cl.transbank.util.HttpUtil;
import cl.transbank.util.WebpayApiResource;
import cl.transbank.webpay.exception.TransactionStatusException;

import java.io.IOException;

public class PatpassComercioTransaction extends BaseTransaction {

    public PatpassComercioTransactionStatusResponse status(String token) throws IOException, TransactionStatusException {
        String endpoint = String.format("%s/status", ApiConstants.PATPASS_COMERCIO_ENDPOINT);
        final WebpayApiRequest request = new PatpassComercioTransactionStatusRequest(token);
        try {
            return WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.POST, request, options, PatpassComercioTransactionStatusResponse.class);
        } catch (TransbankException e) {
            throw new TransactionStatusException(e);
        }
    }

}
