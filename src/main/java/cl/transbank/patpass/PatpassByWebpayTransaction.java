package cl.transbank.patpass;

import cl.transbank.common.ApiConstants;
import cl.transbank.common.BaseTransaction;
import cl.transbank.exception.TransbankException;
import cl.transbank.patpass.requests.TransactionCreateRequest;
import cl.transbank.patpass.responses.PatpassByWebpayTransactionCommitResponse;
import cl.transbank.patpass.responses.PatpassByWebpayTransactionCreateResponse;
import cl.transbank.patpass.responses.PatpassByWebpayTransactionRefundResponse;
import cl.transbank.patpass.responses.PatpassByWebpayTransactionStatusResponse;
import cl.transbank.util.HttpUtil;
import cl.transbank.util.WebpayApiResource;
import cl.transbank.webpay.exception.TransactionCommitException;
import cl.transbank.webpay.exception.TransactionCreateException;
import cl.transbank.webpay.exception.TransactionRefundException;
import cl.transbank.webpay.exception.TransactionStatusException;
import cl.transbank.webpay.common.TransactionRefundRequest;

import java.io.IOException;

abstract class PatpassByWebpayTransaction extends BaseTransaction {

    public PatpassByWebpayTransactionCreateResponse create(
            String buyOrder, String sessionId, double amount, String returnUrl, String serviceId, String cardHolderId,
            String cardHolderName, String cardHolderLastName1, String cardHolderLastName2, String cardHolderMail, String cellphoneNumber,
            String expirationDate, String commerceMail, boolean ufFlag) throws IOException, TransactionCreateException {
        String endpoint = String.format("%s/transactions", ApiConstants.WEBPAY_ENDPOINT);
        final TransactionCreateRequest request = new TransactionCreateRequest(buyOrder, sessionId, amount, returnUrl);
        request.setDetails(serviceId, cardHolderId, cardHolderName, cardHolderLastName1, cardHolderLastName2,
                cardHolderMail, cellphoneNumber, expirationDate, commerceMail, ufFlag);
        try {
            return WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.POST, request, options, PatpassByWebpayTransactionCreateResponse.class);
        } catch (TransbankException e) {
            throw new TransactionCreateException(e);
        }
    }

    public PatpassByWebpayTransactionCommitResponse commit(String token) throws IOException, TransactionCommitException {
        String endpoint = String.format("%s/transactions/%s", ApiConstants.WEBPAY_ENDPOINT,token);
        try {
            return WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.PUT, options, PatpassByWebpayTransactionCommitResponse.class);
        } catch (TransbankException e) {
            throw new TransactionCommitException(e);
        }
    }

    public PatpassByWebpayTransactionRefundResponse refund(String token, double amount) throws IOException, TransactionRefundException {
        String endpoint = String.format("%s/transactions/%s/refunds", ApiConstants.WEBPAY_ENDPOINT,token);
        try {
            return WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.POST, new TransactionRefundRequest(amount), options, PatpassByWebpayTransactionRefundResponse.class);
        } catch (TransbankException e) {
            throw new TransactionRefundException(e);
        }
    }

    public PatpassByWebpayTransactionStatusResponse status(String token) throws IOException, TransactionStatusException {
        String endpoint = String.format("%s/transactions/%s", ApiConstants.WEBPAY_ENDPOINT,token);
        try {
            return WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.GET, options, PatpassByWebpayTransactionStatusResponse.class);
        } catch (TransbankException e) {
            throw new TransactionStatusException(e);
        }
    }



}
