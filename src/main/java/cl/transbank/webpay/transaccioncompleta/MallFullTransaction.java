package cl.transbank.webpay.transaccioncompleta;

import cl.transbank.common.*;
import cl.transbank.exception.TransbankException;
import cl.transbank.model.MallTransactionCreateDetails;
import cl.transbank.model.Options;
import cl.transbank.model.WebpayApiRequest;
import cl.transbank.webpay.common.MallTransactionCaptureRequest;
import cl.transbank.webpay.common.MallTransactionRefundRequest;
import cl.transbank.webpay.transaccioncompleta.model.*;
import cl.transbank.webpay.transaccioncompleta.requests.*;
import cl.transbank.webpay.transaccioncompleta.responses.*;
import cl.transbank.util.HttpUtil;
import cl.transbank.util.WebpayApiResource;
import cl.transbank.webpay.common.WebpayOptions;
import cl.transbank.webpay.exception.*;
import java.io.IOException;

public class MallFullTransaction extends BaseTransaction {

    private static Options defaultOptions = null;

    public MallFullTransaction() {
        this.options = MallFullTransaction.defaultOptions!=null ? MallFullTransaction.defaultOptions : new WebpayOptions(IntegrationCommerceCodes.TRANSACCION_COMPLETA_MALL, IntegrationApiKeys.WEBPAY, IntegrationType.TEST);
    }

    public MallFullTransaction(Options options) {
        this.options = options;
    }

    public MallFullTransactionCreateResponse create(String buyOrder, String sessionId, String cardNumber, String cardExpirationDate, MallTransactionCreateDetails details) throws IOException, TransactionCreateException {
        String endpoint = String.format("%s/transactions", ApiConstants.WEBPAY_ENDPOINT);
        final WebpayApiRequest request = new MallFullTransactionCreateRequest(buyOrder, sessionId, cardNumber, cardExpirationDate, details.getDetails());
        try {
            return WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.POST, request, options, MallFullTransactionCreateResponse.class);
        } catch (TransbankException e) {
            throw new TransactionCreateException(e);
        }
    }

    public MallFullTransactionInstallmentsResponse installments(String token, MallFullTransactionInstallmentsDetails details) throws IOException, TransactionInstallmentException {
        String endpoint = String.format("%s/transactions/%s/installments", ApiConstants.WEBPAY_ENDPOINT, token);
        MallFullTransactionInstallmentsResponse response =  MallFullTransactionInstallmentsResponse.build();
        for(MallFullTransactionInstallmentsDetails.Detail detail: details.getDetails()) {
            final WebpayApiRequest request = new MallFullTransactionInstallmentsRequest(detail.getCommerceCode(), detail.getBuyOrder(), detail.getInstallmentsNumber());
            try {
                response.add(WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.POST, request, options, MallFullTransactionInstallmentResponse.class));
            } catch (TransbankException e) {
                throw new TransactionInstallmentException(e);
            }
        }
        return response;
    }

    public MallFullTransactionCommitResponse commit(String token, MallTransactionCommitDetails details) throws IOException, TransactionCommitException {
        String endpoint = String.format("%s/transactions/%s", ApiConstants.WEBPAY_ENDPOINT, token);
        final WebpayApiRequest request = new MallFullTransactionCommitRequest(details.getDetails());
        try {
            return WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.PUT, request, options, MallFullTransactionCommitResponse.class);
        } catch (TransbankException e) {
            throw new TransactionCommitException(e);
        }
    }


    public MallFullTransactionStatusResponse status(String token) throws IOException, TransactionStatusException {
        String endpoint = String.format("%s/transactions/%s", ApiConstants.WEBPAY_ENDPOINT, token);
        try {
            return WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.GET, options, MallFullTransactionStatusResponse.class);
        } catch (TransbankException e) {
            throw new TransactionStatusException(e);
        }
    }

    public MallFullTransactionRefundResponse refund(String token, String buyOrder, String childCommerceCode, double amount) throws IOException, TransactionRefundException {
        String endpoint = String.format("%s/transactions/%s/refunds", ApiConstants.WEBPAY_ENDPOINT, token);
        final WebpayApiRequest request = new MallTransactionRefundRequest(buyOrder, childCommerceCode, amount);
        try {
            return WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.POST, request, options, MallFullTransactionRefundResponse.class);
        } catch (TransbankException e) {
            throw new TransactionRefundException(e);
        }
    }

    public MallFullTransactionCaptureResponse capture(String token, String commerceCode, String buyOrder, String authorizationCode, double captureAmount) throws IOException, TransactionCaptureException {
        String endpoint = String.format("%s/transactions/%s/capture", ApiConstants.WEBPAY_ENDPOINT, token);
        final WebpayApiRequest request = new MallTransactionCaptureRequest(commerceCode, buyOrder, authorizationCode, captureAmount);
        try {
            return WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.PUT, request, options, MallFullTransactionCaptureResponse.class);
        } catch (TransbankException e) {
            throw new TransactionCaptureException(e);
        }
    }


    /*
    |--------------------------------------------------------------------------
    | Environment Configuration
    |--------------------------------------------------------------------------
    */

    public static void configureForIntegration(String commerceCode, String apiKey){
        MallFullTransaction.defaultOptions = new WebpayOptions(commerceCode, apiKey, IntegrationType.TEST);
    }

    public static void configureForProduction(String commerceCode, String apiKey){
        MallFullTransaction.defaultOptions = new WebpayOptions(commerceCode, apiKey, IntegrationType.LIVE);
    }

    public static void configureForTesting(){
        configureForIntegration(IntegrationCommerceCodes.TRANSACCION_COMPLETA_MALL, IntegrationApiKeys.WEBPAY);
    }

    public static void configureForTestingDeferred(){
        configureForIntegration(IntegrationCommerceCodes.TRANSACCION_COMPLETA_MALL_DEFERRED, IntegrationApiKeys.WEBPAY);
    }
}
