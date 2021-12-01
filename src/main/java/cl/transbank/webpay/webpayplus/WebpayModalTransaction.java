package cl.transbank.webpay.webpayplus;

import cl.transbank.common.ApiConstants;
import cl.transbank.common.BaseTransaction;
import cl.transbank.exception.TransbankException;
import cl.transbank.model.Options;
import cl.transbank.model.WebpayApiRequest;
import cl.transbank.util.HttpUtil;
import cl.transbank.util.WebpayApiResource;
import cl.transbank.webpay.common.TransactionRefundRequest;
import cl.transbank.webpay.exception.*;
import cl.transbank.webpay.webpayplus.requests.ModalTransactionCreateRequest;
import cl.transbank.webpay.webpayplus.responses.*;

import java.io.IOException;

abstract class WebpayModalTransaction extends BaseTransaction {

    public WebpayModalTransaction(Options options){
        this.options = options;
    }

    public WebpayPlusTransactionCreateResponse create(String buyOrder, String sessionId, double amount) throws IOException, TransactionCreateException {
        String endpoint = String.format("%s/transactions", ApiConstants.WEBPAY_ENDPOINT);
        final WebpayApiRequest request = new ModalTransactionCreateRequest(buyOrder, sessionId, amount);
        try {
            return WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.POST, request, options, WebpayPlusTransactionCreateResponse.class);
        } catch (TransbankException e) {
            throw new TransactionCreateException(e);
        }
    }

    public WebpayPlusTransactionCommitResponse commit(String token) throws IOException, TransactionCommitException {
        String endpoint = String.format("%s/transactions/%s", ApiConstants.WEBPAY_ENDPOINT,token);
        try {
            return WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.PUT, options, WebpayPlusTransactionCommitResponse.class);
        } catch (TransbankException e) {
            throw new TransactionCommitException(e);
        }
    }

    public WebpayPlusTransactionStatusResponse status(String token) throws IOException, TransactionStatusException {
        String endpoint = String.format("%s/transactions/%s", ApiConstants.WEBPAY_ENDPOINT,token);
        try {
            return WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.GET, options, WebpayPlusTransactionStatusResponse.class);
        } catch (TransbankException e) {
            throw new TransactionStatusException(e);
        }
    }

    public WebpayPlusTransactionRefundResponse refund(String token, double amount) throws IOException, TransactionRefundException {
        String endpoint = String.format("%s/transactions/%s/refunds", ApiConstants.WEBPAY_ENDPOINT,token);
        try {
            return WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.POST, new TransactionRefundRequest(amount), options, WebpayPlusTransactionRefundResponse.class);
        } catch (TransbankException e) {
            throw new TransactionRefundException(e);
        }
    }

}
