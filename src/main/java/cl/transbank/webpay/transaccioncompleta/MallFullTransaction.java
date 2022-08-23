package cl.transbank.webpay.transaccioncompleta;

import cl.transbank.common.*;
import cl.transbank.exception.TransbankException;
import cl.transbank.model.MallTransactionCreateDetails;
import cl.transbank.model.Options;
import cl.transbank.model.WebpayApiRequest;
import cl.transbank.util.ValidationUtil;
import cl.transbank.webpay.common.MallTransactionCaptureRequest;
import cl.transbank.webpay.common.MallTransactionRefundRequest;
import cl.transbank.webpay.requests.IncreaseAmountRequest;
import cl.transbank.webpay.requests.IncreaseAuthorizationDateRequest;
import cl.transbank.webpay.requests.ReversePreAuthorizedAmountRequest;
import cl.transbank.webpay.responses.DeferredCaptureHistoryResponse;
import cl.transbank.webpay.responses.IncreaseAmountResponse;
import cl.transbank.webpay.responses.IncreaseAuthorizationDateResponse;
import cl.transbank.webpay.responses.ReversePreAuthorizedAmountResponse;
import cl.transbank.webpay.transaccioncompleta.model.*;
import cl.transbank.webpay.transaccioncompleta.requests.*;
import cl.transbank.webpay.transaccioncompleta.responses.*;
import cl.transbank.util.HttpUtil;
import cl.transbank.util.WebpayApiResource;
import cl.transbank.webpay.common.WebpayOptions;
import cl.transbank.webpay.exception.*;
import java.io.IOException;
import java.util.List;

public class MallFullTransaction extends BaseTransaction {

    private static Options defaultOptions = null;

    public MallFullTransaction() {
        this.options = MallFullTransaction.defaultOptions!=null ? MallFullTransaction.defaultOptions : new WebpayOptions(IntegrationCommerceCodes.TRANSACCION_COMPLETA_MALL, IntegrationApiKeys.WEBPAY, IntegrationType.TEST);
    }

    public MallFullTransaction(Options options) {
        this.options = options;
    }

    public MallFullTransactionCreateResponse create(String buyOrder, String sessionId, String cardNumber, String cardExpirationDate, MallTransactionCreateDetails details) throws IOException, TransactionCreateException {
        return create(buyOrder, sessionId, cardNumber, cardExpirationDate, details, null);
    }

    public MallFullTransactionCreateResponse create(String buyOrder, String sessionId, String cardNumber, String cardExpirationDate, MallTransactionCreateDetails details, Short cvv) throws IOException, TransactionCreateException {
        ValidationUtil.hasTextWithMaxLength(buyOrder, ApiConstants.BUY_ORDER_LENGTH, "buyOrder");
        ValidationUtil.hasTextWithMaxLength(sessionId, ApiConstants.SESSION_ID_LENGTH, "sessionId");
        ValidationUtil.hasTextWithMaxLength(cardNumber, ApiConstants.CARD_NUMBER_LENGTH, "cardNumber");
        ValidationUtil.hasTextWithMaxLength(cardExpirationDate, ApiConstants.CARD_EXPIRATION_DATE_LENGTH, "cardExpirationDate");
        ValidationUtil.hasElements(details.getDetails(), "details");

        for(int i=0; i<details.getDetails().size(); i++){
            MallTransactionCreateDetails.Detail item = details.getDetails().get(i);
            ValidationUtil.hasTextWithMaxLength(item.getCommerceCode(), ApiConstants.COMMERCE_CODE_LENGTH, "details.commerceCode");
            ValidationUtil.hasTextWithMaxLength(item.getBuyOrder(), ApiConstants.BUY_ORDER_LENGTH, "details.buyOrder");
        }

        String endpoint = String.format("%s/transactions", ApiConstants.WEBPAY_ENDPOINT);
        final WebpayApiRequest request = new MallFullTransactionCreateRequest(buyOrder, sessionId, cardNumber, cardExpirationDate, details.getDetails(), cvv);
        try {
            return WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.POST, request, options, MallFullTransactionCreateResponse.class);
        } catch (TransbankException e) {
            throw new TransactionCreateException(e);
        }
    }

    public MallFullTransactionInstallmentsResponse installments(String token, MallFullTransactionInstallmentsDetails details) throws IOException, TransactionInstallmentException {
        ValidationUtil.hasTextWithMaxLength(token, ApiConstants.TOKEN_LENGTH, "token");

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
        ValidationUtil.hasTextWithMaxLength(token, ApiConstants.TOKEN_LENGTH, "token");

        String endpoint = String.format("%s/transactions/%s", ApiConstants.WEBPAY_ENDPOINT, token);
        final WebpayApiRequest request = new MallFullTransactionCommitRequest(details.getDetails());
        try {
            return WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.PUT, request, options, MallFullTransactionCommitResponse.class);
        } catch (TransbankException e) {
            throw new TransactionCommitException(e);
        }
    }


    public MallFullTransactionStatusResponse status(String token) throws IOException, TransactionStatusException {
        ValidationUtil.hasTextWithMaxLength(token, ApiConstants.TOKEN_LENGTH, "token");

        String endpoint = String.format("%s/transactions/%s", ApiConstants.WEBPAY_ENDPOINT, token);
        try {
            return WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.GET, options, MallFullTransactionStatusResponse.class);
        } catch (TransbankException e) {
            throw new TransactionStatusException(e);
        }
    }

    public MallFullTransactionRefundResponse refund(String token, String buyOrder, String childCommerceCode, double amount) throws IOException, TransactionRefundException {
        ValidationUtil.hasTextWithMaxLength(token, ApiConstants.TOKEN_LENGTH, "token");
        ValidationUtil.hasTextWithMaxLength(childCommerceCode, ApiConstants.COMMERCE_CODE_LENGTH, "childCommerceCode");
        ValidationUtil.hasTextWithMaxLength(buyOrder, ApiConstants.BUY_ORDER_LENGTH, "buyOrder");

        String endpoint = String.format("%s/transactions/%s/refunds", ApiConstants.WEBPAY_ENDPOINT, token);
        final WebpayApiRequest request = new MallTransactionRefundRequest(buyOrder, childCommerceCode, amount);
        try {
            return WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.POST, request, options, MallFullTransactionRefundResponse.class);
        } catch (TransbankException e) {
            throw new TransactionRefundException(e);
        }
    }

    public MallFullTransactionCaptureResponse capture(String token, String commerceCode, String buyOrder, String authorizationCode, double captureAmount) throws IOException, TransactionCaptureException {
        ValidationUtil.hasTextWithMaxLength(token, ApiConstants.TOKEN_LENGTH, "token");
        ValidationUtil.hasTextWithMaxLength(commerceCode, ApiConstants.COMMERCE_CODE_LENGTH, "commerceCode");
        ValidationUtil.hasTextWithMaxLength(buyOrder, ApiConstants.BUY_ORDER_LENGTH, "buyOrder");
        ValidationUtil.hasTextWithMaxLength(authorizationCode, ApiConstants.AUTHORIZATION_CODE_LENGTH, "authorizationCode");

        String endpoint = String.format("%s/transactions/%s/capture", ApiConstants.WEBPAY_ENDPOINT, token);
        final WebpayApiRequest request = new MallTransactionCaptureRequest(commerceCode, buyOrder, authorizationCode, captureAmount);
        try {
            return WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.PUT, request, options, MallFullTransactionCaptureResponse.class);
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

    public List<DeferredCaptureHistoryResponse> deferredCaptureHistory(String token, String childCommerceCode, String childBuyOrder) throws IOException, DeferredCaptureHistoryException {
        ValidationUtil.hasTextWithMaxLength(token, ApiConstants.TOKEN_LENGTH, "token");
        ValidationUtil.hasTextWithMaxLength(childCommerceCode, ApiConstants.COMMERCE_CODE_LENGTH, "childCommerceCode");
        ValidationUtil.hasTextWithMaxLength(childBuyOrder, ApiConstants.BUY_ORDER_LENGTH, "childBuyOrder");
        WebpayApiRequest request = new IncreaseAuthorizationDateRequest(childCommerceCode, childBuyOrder, null);
        String endpoint = String.format("%s/transactions/%s/details", ApiConstants.WEBPAY_ENDPOINT, token);
        try {
            return WebpayApiResource.executeToList(endpoint, HttpUtil.RequestMethod.POST, request, options, DeferredCaptureHistoryResponse[].class);
        } catch (TransbankException e) {
            throw new DeferredCaptureHistoryException(e);
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
