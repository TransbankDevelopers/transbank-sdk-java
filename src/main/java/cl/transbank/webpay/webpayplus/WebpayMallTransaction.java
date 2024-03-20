package cl.transbank.webpay.webpayplus;

import cl.transbank.common.ApiConstants;
import cl.transbank.common.BaseTransaction;
import cl.transbank.model.Options;
import cl.transbank.exception.TransbankException;
import cl.transbank.model.MallTransactionCreateDetails;
import cl.transbank.model.WebpayApiRequest;
import cl.transbank.util.HttpUtil;
import cl.transbank.util.ValidationUtil;
import cl.transbank.util.WebpayApiResource;
import cl.transbank.webpay.exception.*;
import cl.transbank.webpay.common.MallTransactionCaptureRequest;
import cl.transbank.webpay.webpayplus.requests.MallTransactionCreateRequest;
import cl.transbank.webpay.common.MallTransactionRefundRequest;
import cl.transbank.webpay.webpayplus.responses.*;
import java.io.IOException;

abstract class WebpayMallTransaction extends BaseTransaction {
    public WebpayMallTransaction(Options options){
        this.options = options;
    }

    public WebpayPlusMallTransactionCreateResponse create(String buyOrder, String sessionId, String returnUrl, MallTransactionCreateDetails details) throws IOException, TransactionCreateException {

        ValidationUtil.hasTextWithMaxLength(buyOrder, ApiConstants.BUY_ORDER_LENGTH, "buyOrder");
        ValidationUtil.hasTextWithMaxLength(sessionId, ApiConstants.SESSION_ID_LENGTH, "sessionId");
        ValidationUtil.hasTextWithMaxLength(returnUrl, ApiConstants.RETURN_URL_LENGTH, "returnUrl");
        ValidationUtil.hasElements(details.getDetails(), "details");

        for(int i=0; i<details.getDetails().size(); i++){
            MallTransactionCreateDetails.Detail item = details.getDetails().get(i);
            ValidationUtil.hasTextWithMaxLength(item.getCommerceCode(), ApiConstants.COMMERCE_CODE_LENGTH, "details.commerceCode");
            ValidationUtil.hasTextWithMaxLength(item.getBuyOrder(), ApiConstants.BUY_ORDER_LENGTH, "details.buyOrder");
        }

        String endpoint = String.format("%s/transactions", ApiConstants.WEBPAY_ENDPOINT);
        final WebpayApiRequest request = new MallTransactionCreateRequest(buyOrder, sessionId, returnUrl, details.getDetails());
        try {
            return WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.POST, request, options, WebpayPlusMallTransactionCreateResponse.class);
        } catch (TransbankException e) {
            throw new TransactionCreateException(e);
        }
    }

    public WebpayPlusMallTransactionCommitResponse commit(String token) throws IOException, TransactionCommitException {
        ValidationUtil.hasTextWithMaxLength(token, ApiConstants.TOKEN_LENGTH, ApiConstants.TOKEN_TEXT);

        String endpoint = String.format("%s/transactions/%s", ApiConstants.WEBPAY_ENDPOINT,token);
        try {
            return WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.PUT, options, WebpayPlusMallTransactionCommitResponse.class);
        } catch (TransbankException e) {
            throw new TransactionCommitException(e);
        }
    }

    public WebpayPlusMallTransactionStatusResponse status(String token) throws IOException, TransactionStatusException {
        ValidationUtil.hasTextWithMaxLength(token, ApiConstants.TOKEN_LENGTH, ApiConstants.TOKEN_TEXT);
        String endpoint = String.format("%s/transactions/%s", ApiConstants.WEBPAY_ENDPOINT,token);
        try {
            return WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.GET, options, WebpayPlusMallTransactionStatusResponse.class);
        } catch (TransbankException e) {
            throw new TransactionStatusException(e);
        }
    }

    public WebpayPlusMallTransactionRefundResponse refund(String token, String buyOrder, String childCommerceCode, double amount) throws IOException, TransactionRefundException {
        ValidationUtil.hasTextWithMaxLength(token, ApiConstants.TOKEN_LENGTH, ApiConstants.TOKEN_TEXT);
        ValidationUtil.hasTextWithMaxLength(childCommerceCode, ApiConstants.COMMERCE_CODE_LENGTH, "childCommerceCode");
        ValidationUtil.hasTextWithMaxLength(buyOrder, ApiConstants.BUY_ORDER_LENGTH, "buyOrder");

        String endpoint = String.format("%s/transactions/%s/refunds", ApiConstants.WEBPAY_ENDPOINT,token);
        try {
            return WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.POST, new MallTransactionRefundRequest(buyOrder, childCommerceCode, amount), options, WebpayPlusMallTransactionRefundResponse.class);
        } catch (TransbankException e) {
            throw new TransactionRefundException(e);
        }
    }

    public WebpayPlusMallTransactionCaptureResponse capture(String childCommerceCode, String token, String buyOrder, String authorizationCode, double captureAmount) throws IOException, TransactionCaptureException {
        ValidationUtil.hasTextWithMaxLength(token, ApiConstants.TOKEN_LENGTH, ApiConstants.TOKEN_TEXT);
        ValidationUtil.hasTextWithMaxLength(childCommerceCode, ApiConstants.COMMERCE_CODE_LENGTH, "childCommerceCode");
        ValidationUtil.hasTextWithMaxLength(buyOrder, ApiConstants.BUY_ORDER_LENGTH, "buyOrder");
        ValidationUtil.hasTextWithMaxLength(authorizationCode, ApiConstants.AUTHORIZATION_CODE_LENGTH, "authorizationCode");

        String endpoint = String.format("%s/transactions/%s/capture", ApiConstants.WEBPAY_ENDPOINT, token);
        final WebpayApiRequest request = new MallTransactionCaptureRequest(childCommerceCode, buyOrder, authorizationCode, captureAmount);
        try {
            return WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.PUT, request, options, WebpayPlusMallTransactionCaptureResponse.class);
        } catch (TransbankException e) {
            throw new TransactionCaptureException(e);
        }
    }

}
