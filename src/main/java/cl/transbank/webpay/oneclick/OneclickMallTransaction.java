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
import java.io.IOException;

abstract class OneclickMallTransaction extends BaseTransaction {

    public OneclickMallTransaction(Options options){
        super(options);
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

}
