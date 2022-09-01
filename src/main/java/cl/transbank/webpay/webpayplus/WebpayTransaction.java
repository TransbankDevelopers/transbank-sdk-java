package cl.transbank.webpay.webpayplus;

import cl.transbank.common.ApiConstants;
import cl.transbank.common.BaseTransaction;
import cl.transbank.model.Options;
import cl.transbank.exception.TransbankException;
import cl.transbank.model.WebpayApiRequest;
import cl.transbank.util.HttpUtil;
import cl.transbank.util.ValidationUtil;
import cl.transbank.util.WebpayApiResource;
import cl.transbank.webpay.exception.*;
import cl.transbank.webpay.common.TransactionCaptureRequest;
import cl.transbank.webpay.requests.*;
import cl.transbank.webpay.responses.*;
import cl.transbank.webpay.webpayplus.requests.TransactionCreateRequest;
import cl.transbank.webpay.common.TransactionRefundRequest;
import cl.transbank.webpay.webpayplus.responses.*;

import java.io.IOException;
import java.util.List;

abstract class WebpayTransaction extends BaseTransaction {

    public WebpayTransaction(Options options){
        this.options = options;
    }

    public WebpayPlusTransactionCreateResponse create(String buyOrder, String sessionId, double amount, String returnUrl) throws IOException, TransactionCreateException {

        ValidationUtil.hasTextWithMaxLength(buyOrder, ApiConstants.BUY_ORDER_LENGTH, "buyOrder");
        ValidationUtil.hasTextWithMaxLength(sessionId, ApiConstants.SESSION_ID_LENGTH, "sessionId");
        ValidationUtil.hasTextWithMaxLength(returnUrl, ApiConstants.RETURN_URL_LENGTH, "returnUrl");

        String endpoint = String.format("%s/transactions", ApiConstants.WEBPAY_ENDPOINT);
        final WebpayApiRequest request = new TransactionCreateRequest(buyOrder, sessionId, amount, returnUrl);
        try {
            return WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.POST, request, options, WebpayPlusTransactionCreateResponse.class);
        } catch (TransbankException e) {
            throw new TransactionCreateException(e);
        }
    }

    public WebpayPlusTransactionCommitResponse commit(String token) throws IOException, TransactionCommitException {

        ValidationUtil.hasTextWithMaxLength(token, ApiConstants.TOKEN_LENGTH, "token");

        String endpoint = String.format("%s/transactions/%s", ApiConstants.WEBPAY_ENDPOINT,token);
        try {
            return WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.PUT, options, WebpayPlusTransactionCommitResponse.class);
        } catch (TransbankException e) {
            throw new TransactionCommitException(e);
        }
    }

    public WebpayPlusTransactionStatusResponse status(String token) throws IOException, TransactionStatusException {
        ValidationUtil.hasTextWithMaxLength(token, ApiConstants.TOKEN_LENGTH, "token");
        String endpoint = String.format("%s/transactions/%s", ApiConstants.WEBPAY_ENDPOINT,token);
        try {
            return WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.GET, options, WebpayPlusTransactionStatusResponse.class);
        } catch (TransbankException e) {
            throw new TransactionStatusException(e);
        }
    }

    public WebpayPlusTransactionRefundResponse refund(String token, double amount) throws IOException, TransactionRefundException {
        ValidationUtil.hasTextWithMaxLength(token, ApiConstants.TOKEN_LENGTH, "token");
        String endpoint = String.format("%s/transactions/%s/refunds", ApiConstants.WEBPAY_ENDPOINT,token);
        try {
            return WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.POST, new TransactionRefundRequest(amount), options, WebpayPlusTransactionRefundResponse.class);
        } catch (TransbankException e) {
            throw new TransactionRefundException(e);
        }
    }

    public WebpayPlusTransactionCaptureResponse capture(String token, String buyOrder, String authorizationCode, double captureAmount) throws IOException, TransactionCaptureException {
        ValidationUtil.hasTextWithMaxLength(token, ApiConstants.TOKEN_LENGTH, "token");
        ValidationUtil.hasTextWithMaxLength(buyOrder, ApiConstants.BUY_ORDER_LENGTH, "buyOrder");
        ValidationUtil.hasTextWithMaxLength(authorizationCode, ApiConstants.AUTHORIZATION_CODE_LENGTH, "authorizationCode");
        String endpoint = String.format("%s/transactions/%s/capture", ApiConstants.WEBPAY_ENDPOINT, token);
        final WebpayApiRequest request = new TransactionCaptureRequest(buyOrder, authorizationCode, captureAmount);
        try {
            return WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.PUT, request, options, WebpayPlusTransactionCaptureResponse.class);
        } catch (TransbankException e) {
            throw new TransactionCaptureException(e);
        }
    }

    public IncreaseAmountResponse increaseAmount(String token, String buyOrder, String authorizationCode, double amount) throws IOException, IncreaseAmountException {
        ValidationUtil.hasTextWithMaxLength(token, ApiConstants.TOKEN_LENGTH, "token");
        ValidationUtil.hasTextWithMaxLength(buyOrder, ApiConstants.BUY_ORDER_LENGTH, "buyOrder");
        ValidationUtil.hasTextWithMaxLength(authorizationCode, ApiConstants.AUTHORIZATION_CODE_LENGTH, "authorizationCode");
        WebpayApiRequest request = new IncreaseAmountRequest(options.getCommerceCode(), buyOrder, authorizationCode, amount);
        String endpoint = String.format("%s/transactions/%s/amount", ApiConstants.WEBPAY_ENDPOINT, token);
        try {
            return WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.PUT, request, options, IncreaseAmountResponse.class);
        } catch (TransbankException e) {
            throw new IncreaseAmountException(e);
        }
    }

    public IncreaseAuthorizationDateResponse increaseAuthorizationDate(String token, String buyOrder, String authorizationCode) throws IOException, IncreaseAuthorizationDateException {
        ValidationUtil.hasTextWithMaxLength(token, ApiConstants.TOKEN_LENGTH, "token");
        ValidationUtil.hasTextWithMaxLength(buyOrder, ApiConstants.BUY_ORDER_LENGTH, "buyOrder");
        ValidationUtil.hasTextWithMaxLength(authorizationCode, ApiConstants.AUTHORIZATION_CODE_LENGTH, "authorizationCode");
        WebpayApiRequest request = new IncreaseAuthorizationDateRequest(options.getCommerceCode(), buyOrder, authorizationCode);
        String endpoint = String.format("%s/transactions/%s/authorization_date", ApiConstants.WEBPAY_ENDPOINT, token);
        try {
            return WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.PUT, request, options, IncreaseAuthorizationDateResponse.class);
        } catch (TransbankException e) {
            throw new IncreaseAuthorizationDateException(e);
        }
    }

    public ReversePreAuthorizedAmountResponse reversePreAuthorizedAmount(String token, String buyOrder, String authorizationCode, double amount) throws IOException, ReversePreAuthorizedAmountException {
        ValidationUtil.hasTextWithMaxLength(token, ApiConstants.TOKEN_LENGTH, "token");
        ValidationUtil.hasTextWithMaxLength(buyOrder, ApiConstants.BUY_ORDER_LENGTH, "buyOrder");
        ValidationUtil.hasTextWithMaxLength(authorizationCode, ApiConstants.AUTHORIZATION_CODE_LENGTH, "authorizationCode");
        WebpayApiRequest request = new ReversePreAuthorizedAmountRequest(options.getCommerceCode(), buyOrder, authorizationCode, amount);
        String endpoint = String.format("%s/transactions/%s/reverse/amount", ApiConstants.WEBPAY_ENDPOINT, token);
        try {
            return WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.PUT, request, options, ReversePreAuthorizedAmountResponse.class);
        } catch (TransbankException e) {
            throw new ReversePreAuthorizedAmountException(e);
        }
    }

    public List<DeferredCaptureHistoryResponse> deferredCaptureHistory(String token) throws IOException, DeferredCaptureHistoryException {
        ValidationUtil.hasTextWithMaxLength(token, ApiConstants.TOKEN_LENGTH, "token");
        String endpoint = String.format("%s/transactions/%s/details", ApiConstants.WEBPAY_ENDPOINT,token);
        try {
            return WebpayApiResource.executeToList(endpoint, HttpUtil.RequestMethod.GET, options, DeferredCaptureHistoryResponse[].class);
        } catch (TransbankException e) {
            throw new DeferredCaptureHistoryException(e);
        }
    }
}
