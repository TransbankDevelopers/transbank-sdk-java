package cl.transbank.webpay.transaccioncompleta;

import cl.transbank.common.*;
import cl.transbank.exception.TransbankException;
import cl.transbank.model.Options;
import cl.transbank.model.WebpayApiRequest;
import cl.transbank.util.ValidationUtil;
import cl.transbank.webpay.common.TransactionCaptureRequest;
import cl.transbank.webpay.common.TransactionRefundRequest;
import cl.transbank.webpay.transaccioncompleta.requests.*;
import cl.transbank.webpay.transaccioncompleta.responses.*;
import cl.transbank.util.HttpUtil;
import cl.transbank.util.WebpayApiResource;
import cl.transbank.webpay.common.WebpayOptions;
import cl.transbank.webpay.exception.*;

import java.io.IOException;

public class FullTransaction extends BaseTransaction {

    private static Options defaultOptions = null;

    public FullTransaction() {
        this.options = FullTransaction.defaultOptions!=null ? FullTransaction.defaultOptions : new WebpayOptions(IntegrationCommerceCodes.TRANSACCION_COMPLETA, IntegrationApiKeys.WEBPAY, IntegrationType.TEST);
    }

    public FullTransaction(Options options) {
        this.options = options;
    }

    public FullTransactionCreateResponse create(String buyOrder, String sessionId, double amount, short cvv, String cardNumber, String cardExpirationDate) throws IOException, TransactionCreateException {
        ValidationUtil.hasTextWithMaxLength(buyOrder, ApiConstants.BUY_ORDER_LENGTH, "buyOrder");
        ValidationUtil.hasTextWithMaxLength(sessionId, ApiConstants.SESSION_ID_LENGTH, "sessionId");
        ValidationUtil.hasTextWithMaxLength(cardNumber, ApiConstants.CARD_NUMBER_LENGTH, "cardNumber");
        ValidationUtil.hasTextWithMaxLength(cardExpirationDate, ApiConstants.CARD_EXPIRATION_DATE_LENGTH, "cardExpirationDate");

        String endpoint = String.format("%s/transactions", ApiConstants.WEBPAY_ENDPOINT);
        final WebpayApiRequest request = new TransactionCreateRequest(buyOrder, sessionId, amount, cardNumber, cvv, cardExpirationDate);
        try {
            return WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.POST, request, options, FullTransactionCreateResponse.class);
        } catch (TransbankException e) {
            throw new TransactionCreateException(e);
        }
    }

    public FullTransactionInstallmentResponse installments(String token, byte installmentsNumber) throws  IOException, TransactionInstallmentException {
        ValidationUtil.hasTextWithMaxLength(token, ApiConstants.TOKEN_LENGTH, "token");
        String endpoint = String.format("%s/transactions/%s/installments", ApiConstants.WEBPAY_ENDPOINT, token);
        final WebpayApiRequest request = new TransactionInstallmentsRequest(installmentsNumber);
        try {
            return WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.POST, request, options, FullTransactionInstallmentResponse.class);
        } catch (TransbankException e) {
            throw new TransactionInstallmentException(e);
        }
    }

    public FullTransactionCommitResponse commit(String token, Long idQueryInstallments, Byte deferredPeriodIndex, Boolean gracePeriod) throws IOException, TransactionCommitException {
        ValidationUtil.hasTextWithMaxLength(token, ApiConstants.TOKEN_LENGTH, "token");
        String endpoint = String.format("%s/transactions/%s", ApiConstants.WEBPAY_ENDPOINT, token);
        final WebpayApiRequest request = new TransactionCommitRequest(idQueryInstallments, deferredPeriodIndex, gracePeriod);
        try {
            return WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.PUT, request, options, FullTransactionCommitResponse.class);
        } catch (TransbankException e) {
            throw new TransactionCommitException(e);
        }
    }

    public FullTransactionStatusResponse status(String token) throws IOException, TransactionStatusException {
        ValidationUtil.hasTextWithMaxLength(token, ApiConstants.TOKEN_LENGTH, "token");
        String endpoint = String.format("%s/transactions/%s", ApiConstants.WEBPAY_ENDPOINT, token);
        try {
            return WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.GET, options, FullTransactionStatusResponse.class);
        } catch (TransbankException e) {
            throw new TransactionStatusException(e);
        }
    }

    public FullTransactionRefundResponse refund(String token, double amount) throws IOException, TransactionRefundException {
        ValidationUtil.hasTextWithMaxLength(token, ApiConstants.TOKEN_LENGTH, "token");
        String endpoint = String.format("%s/transactions/%s/refunds", ApiConstants.WEBPAY_ENDPOINT, token);
        final WebpayApiRequest request = new TransactionRefundRequest(amount);
        try {
            return WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.POST, request, options, FullTransactionRefundResponse.class);
        } catch (TransbankException e) {
            throw new TransactionRefundException(e);
        }
    }

    public FullTransactionCaptureResponse capture(String token, String buyOrder, String authorizationCode, double captureAmount) throws IOException, TransactionCaptureException {
        ValidationUtil.hasTextWithMaxLength(token, ApiConstants.TOKEN_LENGTH, "token");
        ValidationUtil.hasTextWithMaxLength(buyOrder, ApiConstants.BUY_ORDER_LENGTH, "buyOrder");
        ValidationUtil.hasTextWithMaxLength(authorizationCode, ApiConstants.AUTHORIZATION_CODE_LENGTH, "authorizationCode");

        String endpoint = String.format("%s/transactions/%s/capture", ApiConstants.WEBPAY_ENDPOINT, token);
        final  WebpayApiRequest request = new TransactionCaptureRequest(buyOrder, authorizationCode, captureAmount);
        try {
            return WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.PUT, request, options, FullTransactionCaptureResponse.class);
        } catch (TransbankException e){
            throw  new TransactionCaptureException(e);
        }

    }




    /*
    |--------------------------------------------------------------------------
    | Environment Configuration
    |--------------------------------------------------------------------------
    */

    public static void configureForIntegration(String commerceCode, String apiKey){
        FullTransaction.defaultOptions = new WebpayOptions(commerceCode, apiKey, IntegrationType.TEST);
    }

    public static void configureForProduction(String commerceCode, String apiKey){
        FullTransaction.defaultOptions = new WebpayOptions(commerceCode, apiKey, IntegrationType.LIVE);
    }

    public static void configureForTesting(){
        configureForIntegration(IntegrationCommerceCodes.TRANSACCION_COMPLETA, IntegrationApiKeys.WEBPAY);
    }

    public static void configureForTestingDeferred(){
        configureForIntegration(IntegrationCommerceCodes.TRANSACCION_COMPLETA_DEFERRED, IntegrationApiKeys.WEBPAY);
    }

}
