package cl.transbank.webpay.oneclick;

import cl.transbank.common.ApiConstants;
import cl.transbank.common.BaseTransaction;
import cl.transbank.model.Options;
import cl.transbank.exception.TransbankException;
import cl.transbank.model.WebpayApiRequest;
import cl.transbank.util.HttpUtil;
import cl.transbank.util.ValidationUtil;
import cl.transbank.util.WebpayApiResource;
import cl.transbank.webpay.common.MallTransactionCaptureRequest;
import cl.transbank.webpay.exception.*;
import cl.transbank.webpay.oneclick.model.MallTransactionCreateDetails;
import cl.transbank.webpay.oneclick.requests.*;
import cl.transbank.webpay.oneclick.responses.*;
import cl.transbank.webpay.requests.IncreaseAmountRequest;
import cl.transbank.webpay.requests.IncreaseAuthorizationDateRequest;
import cl.transbank.webpay.requests.ReversePreAuthorizedAmountRequest;
import cl.transbank.webpay.responses.*;


import java.io.IOException;
import java.util.List;


abstract class OneclickMallTransaction extends BaseTransaction {

    public OneclickMallTransaction(Options options){
        this.options = options;
    }
    public OneclickMallTransactionAuthorizeResponse authorize(String username, String tbkUser, String parentBuyOrder, MallTransactionCreateDetails details) throws IOException, TransactionAuthorizeException {

        ValidationUtil.hasTextWithMaxLength(username, ApiConstants.USER_NAME_LENGTH, "username");
        ValidationUtil.hasTextWithMaxLength(tbkUser, ApiConstants.TBK_USER_LENGTH, "tbkUser");
        ValidationUtil.hasTextWithMaxLength(parentBuyOrder, ApiConstants.BUY_ORDER_LENGTH, "parentBuyOrder");
        ValidationUtil.hasElements(details.getDetails(), "details");

        for(int i=0; i<details.getDetails().size(); i++){
            MallTransactionCreateDetails.Detail item = details.getDetails().get(i);
            ValidationUtil.hasTextWithMaxLength(item.getCommerceCode(), ApiConstants.COMMERCE_CODE_LENGTH, "details.commerceCode");
            ValidationUtil.hasTextWithMaxLength(item.getBuyOrder(), ApiConstants.BUY_ORDER_LENGTH, "details.buyOrder");
        }

        WebpayApiRequest request = new TransactionAuthorizeRequest(username, tbkUser, parentBuyOrder, details.getDetails());
        String endpoint = String.format("%s/transactions", ApiConstants.ONECLICK_ENDPOINT);
        try {
            return WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.POST, request, options, OneclickMallTransactionAuthorizeResponse.class);
        } catch (TransbankException e) {
            throw new TransactionAuthorizeException(e);
        }
    }

    public OneclickMallTransactionRefundResponse refund(String buyOrder, String childCommerceCode, String childBuyOrder, double amount) throws IOException, TransactionRefundException {
        ValidationUtil.hasTextWithMaxLength(childCommerceCode, ApiConstants.COMMERCE_CODE_LENGTH, "childCommerceCode");
        ValidationUtil.hasTextWithMaxLength(buyOrder, ApiConstants.BUY_ORDER_LENGTH, "buyOrder");
        ValidationUtil.hasTextWithMaxLength(childBuyOrder, ApiConstants.BUY_ORDER_LENGTH, "childBuyOrder");

        WebpayApiRequest request = new MallTransactionRefundRequest(childCommerceCode, childBuyOrder, amount);
        String endpoint = String.format("%s/transactions/%s/refunds", ApiConstants.ONECLICK_ENDPOINT, buyOrder);
        try {
            return WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.POST, request, options, OneclickMallTransactionRefundResponse.class);
        } catch (TransbankException e) {
            throw new TransactionRefundException(e);
        }
    }

    public OneclickMallTransactionStatusResponse status(String buyOrder) throws IOException, TransactionStatusException {
        ValidationUtil.hasTextWithMaxLength(buyOrder, ApiConstants.BUY_ORDER_LENGTH, "buyOrder");
        String endpoint = String.format("%s/transactions/%s", ApiConstants.ONECLICK_ENDPOINT, buyOrder);
        try {
            return WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.GET, options, OneclickMallTransactionStatusResponse.class);
        } catch (TransbankException e) {
            throw new TransactionStatusException(e);
        }
    }

    public OneclickMallTransactionCaptureResponse capture(String childCommerceCode, String childBuyOrder, String authorizationCode, double captureAmount) throws IOException, TransactionCaptureException {
        ValidationUtil.hasTextWithMaxLength(childCommerceCode, ApiConstants.COMMERCE_CODE_LENGTH, "childCommerceCode");
        ValidationUtil.hasTextWithMaxLength(childBuyOrder, ApiConstants.BUY_ORDER_LENGTH, "childBuyOrder");
        ValidationUtil.hasTextWithMaxLength(authorizationCode, ApiConstants.AUTHORIZATION_CODE_LENGTH, "authorizationCode");
        WebpayApiRequest request = new MallTransactionCaptureRequest(childCommerceCode, childBuyOrder, authorizationCode, captureAmount);
        String endpoint = String.format("%s/transactions/capture", ApiConstants.ONECLICK_ENDPOINT);
        try {
            return WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.PUT, request, options, OneclickMallTransactionCaptureResponse.class);
        } catch (TransbankException e) {
            throw new TransactionCaptureException(e);
        }
    }

    public IncreaseAmountResponse increaseAmount(String childCommerceCode, String childBuyOrder, String authorizationCode, double amount) throws IOException, IncreaseAmountException {
        ValidationUtil.hasTextWithMaxLength(childCommerceCode, ApiConstants.COMMERCE_CODE_LENGTH, "childCommerceCode");
        ValidationUtil.hasTextWithMaxLength(childBuyOrder, ApiConstants.BUY_ORDER_LENGTH, "childBuyOrder");
        ValidationUtil.hasTextWithMaxLength(authorizationCode, ApiConstants.AUTHORIZATION_CODE_LENGTH, "authorizationCode");
        WebpayApiRequest request = new IncreaseAmountRequest(childCommerceCode, childBuyOrder, authorizationCode, amount);
        String endpoint = String.format("%s/transactions/amount", ApiConstants.ONECLICK_ENDPOINT);
        try {
            return WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.PUT, request, options, IncreaseAmountResponse.class);
        } catch (TransbankException e) {
            throw new IncreaseAmountException(e);
        }
    }

    public IncreaseAuthorizationDateResponse increaseAuthorizationDate(String childCommerceCode, String childBuyOrder, String authorizationCode) throws IOException, IncreaseAuthorizationDateException {
        ValidationUtil.hasTextWithMaxLength(childCommerceCode, ApiConstants.COMMERCE_CODE_LENGTH, "childCommerceCode");
        ValidationUtil.hasTextWithMaxLength(childBuyOrder, ApiConstants.BUY_ORDER_LENGTH, "childBuyOrder");
        ValidationUtil.hasTextWithMaxLength(authorizationCode, ApiConstants.AUTHORIZATION_CODE_LENGTH, "authorizationCode");
        WebpayApiRequest request = new IncreaseAuthorizationDateRequest(childCommerceCode, childBuyOrder, authorizationCode);
        String endpoint = String.format("%s/transactions/authorization_date", ApiConstants.ONECLICK_ENDPOINT);
        try {
            return WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.PUT, request, options, IncreaseAuthorizationDateResponse.class);
        } catch (TransbankException e) {
            throw new IncreaseAuthorizationDateException(e);
        }
    }

    public ReversePreAuthorizedAmountResponse reversePreAuthorizedAmount(String childCommerceCode, String childBuyOrder, String authorizationCode, double amount) throws IOException, ReversePreAuthorizedAmountException {
        ValidationUtil.hasTextWithMaxLength(childCommerceCode, ApiConstants.COMMERCE_CODE_LENGTH, "childCommerceCode");
        ValidationUtil.hasTextWithMaxLength(childBuyOrder, ApiConstants.BUY_ORDER_LENGTH, "childBuyOrder");
        ValidationUtil.hasTextWithMaxLength(authorizationCode, ApiConstants.AUTHORIZATION_CODE_LENGTH, "authorizationCode");
        WebpayApiRequest request = new ReversePreAuthorizedAmountRequest(childCommerceCode, childBuyOrder, authorizationCode, amount);
        String endpoint = String.format("%s/transactions/reverse/amount", ApiConstants.ONECLICK_ENDPOINT);
        try {
            return WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.PUT, request, options, ReversePreAuthorizedAmountResponse.class);
        } catch (TransbankException e) {
            throw new ReversePreAuthorizedAmountException(e);
        }
    }

    public List<DeferredCaptureHistoryResponse> deferredCaptureHistory(String childCommerceCode, String childBuyOrder, String authorizationCode) throws IOException, DeferredCaptureHistoryException {
        ValidationUtil.hasTextWithMaxLength(childCommerceCode, ApiConstants.COMMERCE_CODE_LENGTH, "childCommerceCode");
        ValidationUtil.hasTextWithMaxLength(childBuyOrder, ApiConstants.BUY_ORDER_LENGTH, "childBuyOrder");
        ValidationUtil.hasTextWithMaxLength(authorizationCode, ApiConstants.AUTHORIZATION_CODE_LENGTH, "authorizationCode");
        WebpayApiRequest request = new IncreaseAuthorizationDateRequest(childCommerceCode, childBuyOrder, authorizationCode);
        String endpoint = String.format("%s/transactions/details", ApiConstants.ONECLICK_ENDPOINT);
        try {
            return WebpayApiResource.executeToList(endpoint, HttpUtil.RequestMethod.POST, request, options, DeferredCaptureHistoryResponse[].class);
        } catch (TransbankException e) {
            throw new DeferredCaptureHistoryException(e);
        }
    }
}
