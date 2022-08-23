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
import cl.transbank.webpay.requests.IncreaseAmountRequest;
import cl.transbank.webpay.requests.IncreaseAuthorizationDateRequest;
import cl.transbank.webpay.requests.ReversePreAuthorizedAmountRequest;
import cl.transbank.webpay.responses.DeferredCaptureHistoryResponse;
import cl.transbank.webpay.responses.IncreaseAmountResponse;
import cl.transbank.webpay.responses.IncreaseAuthorizationDateResponse;
import cl.transbank.webpay.responses.ReversePreAuthorizedAmountResponse;
import cl.transbank.webpay.webpayplus.requests.MallTransactionCreateRequest;
import cl.transbank.webpay.common.MallTransactionRefundRequest;
import cl.transbank.webpay.webpayplus.responses.*;

import java.io.IOException;
import java.util.List;

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
        ValidationUtil.hasTextWithMaxLength(token, ApiConstants.TOKEN_LENGTH, "token");

        String endpoint = String.format("%s/transactions/%s", ApiConstants.WEBPAY_ENDPOINT,token);
        try {
            return WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.PUT, options, WebpayPlusMallTransactionCommitResponse.class);
        } catch (TransbankException e) {
            throw new TransactionCommitException(e);
        }
    }

    public WebpayPlusMallTransactionStatusResponse status(String token) throws IOException, TransactionStatusException {
        ValidationUtil.hasTextWithMaxLength(token, ApiConstants.TOKEN_LENGTH, "token");
        String endpoint = String.format("%s/transactions/%s", ApiConstants.WEBPAY_ENDPOINT,token);
        try {
            return WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.GET, options, WebpayPlusMallTransactionStatusResponse.class);
        } catch (TransbankException e) {
            throw new TransactionStatusException(e);
        }
    }

    public WebpayPlusMallTransactionRefundResponse refund(String token, String buyOrder, String childCommerceCode, double amount) throws IOException, TransactionRefundException {
        ValidationUtil.hasTextWithMaxLength(token, ApiConstants.TOKEN_LENGTH, "token");
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
        ValidationUtil.hasTextWithMaxLength(token, ApiConstants.TOKEN_LENGTH, "token");
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

    public IncreaseAmountResponse increaseAmount(String token, String childCommerceCode, String childBuyOrder, String authorizationCode, double amount) throws IOException, IncreaseAmountException {
        ValidationUtil.hasTextWithMaxLength(token, ApiConstants.TOKEN_LENGTH, "token");
        ValidationUtil.hasTextWithMaxLength(childCommerceCode, ApiConstants.COMMERCE_CODE_LENGTH, "childCommerceCode");
        ValidationUtil.hasTextWithMaxLength(childBuyOrder, ApiConstants.BUY_ORDER_LENGTH, "childBuyOrder");
        ValidationUtil.hasTextWithMaxLength(authorizationCode, ApiConstants.AUTHORIZATION_CODE_LENGTH, "authorizationCode");
        WebpayApiRequest request = new IncreaseAmountRequest(childCommerceCode, childBuyOrder, authorizationCode, amount);
        String endpoint = String.format("%s/transactions/%s/amount", ApiConstants.WEBPAY_ENDPOINT, token);
        try {
            return WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.PUT, request, options, IncreaseAmountResponse.class);
        } catch (TransbankException e) {
            throw new IncreaseAmountException(e);
        }
    }

    public IncreaseAuthorizationDateResponse increaseAuthorizationDate(String token, String childCommerceCode, String childBuyOrder, String authorizationCode) throws IOException, IncreaseAuthorizationDateException {
        ValidationUtil.hasTextWithMaxLength(token, ApiConstants.TOKEN_LENGTH, "token");
        ValidationUtil.hasTextWithMaxLength(childCommerceCode, ApiConstants.COMMERCE_CODE_LENGTH, "childCommerceCode");
        ValidationUtil.hasTextWithMaxLength(childBuyOrder, ApiConstants.BUY_ORDER_LENGTH, "childBuyOrder");
        ValidationUtil.hasTextWithMaxLength(authorizationCode, ApiConstants.AUTHORIZATION_CODE_LENGTH, "authorizationCode");
        WebpayApiRequest request = new IncreaseAuthorizationDateRequest(childCommerceCode, childBuyOrder, authorizationCode);
        String endpoint = String.format("%s/transactions/%s/authorization_date", ApiConstants.WEBPAY_ENDPOINT, token);
        try {
            return WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.PUT, request, options, IncreaseAuthorizationDateResponse.class);
        } catch (TransbankException e) {
            throw new IncreaseAuthorizationDateException(e);
        }
    }

    public ReversePreAuthorizedAmountResponse reversePreAuthorizedAmount(String token, String childCommerceCode, String childBuyOrder, String authorizationCode, double amount) throws IOException, ReversePreAuthorizedAmountException {
        ValidationUtil.hasTextWithMaxLength(token, ApiConstants.TOKEN_LENGTH, "token");
        ValidationUtil.hasTextWithMaxLength(childCommerceCode, ApiConstants.COMMERCE_CODE_LENGTH, "childCommerceCode");
        ValidationUtil.hasTextWithMaxLength(childBuyOrder, ApiConstants.BUY_ORDER_LENGTH, "childBuyOrder");
        ValidationUtil.hasTextWithMaxLength(authorizationCode, ApiConstants.AUTHORIZATION_CODE_LENGTH, "authorizationCode");
        WebpayApiRequest request = new ReversePreAuthorizedAmountRequest(childCommerceCode, childBuyOrder, authorizationCode, amount);
        String endpoint = String.format("%s/transactions/%s/reverse/amount", ApiConstants.WEBPAY_ENDPOINT, token);
        try {
            return WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.PUT, request, options, ReversePreAuthorizedAmountResponse.class);
        } catch (TransbankException e) {
            throw new ReversePreAuthorizedAmountException(e);
        }
    }

    public List<DeferredCaptureHistoryResponse> deferredCaptureHistory(String token, String childCommerceCode, String childBuyOrder, String authorizationCode) throws IOException, DeferredCaptureHistoryException {
        ValidationUtil.hasTextWithMaxLength(token, ApiConstants.TOKEN_LENGTH, "token");
        ValidationUtil.hasTextWithMaxLength(childCommerceCode, ApiConstants.COMMERCE_CODE_LENGTH, "childCommerceCode");
        ValidationUtil.hasTextWithMaxLength(childBuyOrder, ApiConstants.BUY_ORDER_LENGTH, "childBuyOrder");
        ValidationUtil.hasTextWithMaxLength(authorizationCode, ApiConstants.AUTHORIZATION_CODE_LENGTH, "authorizationCode");
        WebpayApiRequest request = new IncreaseAuthorizationDateRequest(childCommerceCode, childBuyOrder, authorizationCode);
        String endpoint = String.format("%s/transactions/%s/details", ApiConstants.WEBPAY_ENDPOINT, token);
        try {
            return WebpayApiResource.executeToList(endpoint, HttpUtil.RequestMethod.POST, request, options, DeferredCaptureHistoryResponse[].class);
        } catch (TransbankException e) {
            throw new DeferredCaptureHistoryException(e);
        }
    }
}
