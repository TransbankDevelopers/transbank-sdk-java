package cl.transbank.webpay.oneclick;

import cl.transbank.common.ApiConstants;
import cl.transbank.common.BaseTransaction;
import cl.transbank.model.Options;
import cl.transbank.exception.TransbankException;
import cl.transbank.model.WebpayApiRequest;
import cl.transbank.util.HttpUtil;
import cl.transbank.util.WebpayApiResource;
import cl.transbank.webpay.common.MallTransactionCaptureRequest;
import cl.transbank.webpay.exception.TransactionAuthorizeException;
import cl.transbank.webpay.exception.TransactionCaptureException;
import cl.transbank.webpay.exception.TransactionRefundException;
import cl.transbank.webpay.exception.TransactionStatusException;
import cl.transbank.webpay.oneclick.model.MallTransactionCreateDetails;
import cl.transbank.webpay.oneclick.requests.MallTransactionRefundRequest;
import cl.transbank.webpay.oneclick.requests.TransactionAuthorizeRequest;
import cl.transbank.webpay.oneclick.responses.OneclickMallTransactionAuthorizeResponse;
import cl.transbank.webpay.oneclick.responses.OneclickMallTransactionCaptureResponse;
import cl.transbank.webpay.oneclick.responses.OneclickMallTransactionRefundResponse;
import cl.transbank.webpay.oneclick.responses.OneclickMallTransactionStatusResponse;

import java.io.IOException;


abstract class OneclickMallTransaction extends BaseTransaction {

    public OneclickMallTransaction(Options options){
        this.options = options;
    }
    public OneclickMallTransactionAuthorizeResponse authorize(String username, String tbkUser, String parentBuyOrder, MallTransactionCreateDetails details) throws IOException, TransactionAuthorizeException {

        WebpayApiRequest request = new TransactionAuthorizeRequest(username, tbkUser, parentBuyOrder, details.getDetails());
        String endpoint = String.format("%s/transactions", ApiConstants.ONECLICK_ENDPOINT);
        try {
            return WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.POST, request, options, OneclickMallTransactionAuthorizeResponse.class);
        } catch (TransbankException e) {
            throw new TransactionAuthorizeException(e);
        }
    }

    public OneclickMallTransactionRefundResponse refund(String buyOrder, String childCommerceCode, String childBuyOrder, double amount) throws IOException, TransactionRefundException {
        WebpayApiRequest request = new MallTransactionRefundRequest(childCommerceCode, childBuyOrder, amount);
        String endpoint = String.format("%s/transactions/%s/refunds", ApiConstants.ONECLICK_ENDPOINT, buyOrder);
        try {
            return WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.POST, request, options, OneclickMallTransactionRefundResponse.class);
        } catch (TransbankException e) {
            throw new TransactionRefundException(e);
        }
    }

    public OneclickMallTransactionStatusResponse status(String buyOrder) throws IOException, TransactionStatusException {
        String endpoint = String.format("%s/transactions/%s", ApiConstants.ONECLICK_ENDPOINT, buyOrder);
        try {
            return WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.GET, options, OneclickMallTransactionStatusResponse.class);
        } catch (TransbankException e) {
            throw new TransactionStatusException(e);
        }
    }

    public OneclickMallTransactionCaptureResponse capture(String childCommerceCode, String childBuyOrder, String authorizationCode, double captureAmount) throws IOException, TransactionCaptureException {
        WebpayApiRequest request = new MallTransactionCaptureRequest(childCommerceCode, childBuyOrder, authorizationCode, captureAmount);
        String endpoint = String.format("%s/transactions/capture", ApiConstants.ONECLICK_ENDPOINT);
        try {
            return WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.PUT, request, options, OneclickMallTransactionCaptureResponse.class);
        } catch (TransbankException e) {
            throw new TransactionCaptureException(e);
        }
    }
}
