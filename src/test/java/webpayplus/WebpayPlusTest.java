package webpayplus;

import cl.transbank.common.ApiConstants;
import cl.transbank.common.IntegrationApiKeys;
import cl.transbank.common.IntegrationCommerceCodes;
import cl.transbank.common.IntegrationType;
import cl.transbank.model.CardDetail;
import cl.transbank.model.Options;
import cl.transbank.webpay.common.WebpayOptions;
import cl.transbank.webpay.exception.TransactionCommitException;
import cl.transbank.webpay.exception.TransactionCreateException;
import cl.transbank.webpay.exception.TransactionRefundException;
import cl.transbank.webpay.exception.TransactionStatusException;
import cl.transbank.webpay.webpayplus.WebpayPlus;
import cl.transbank.webpay.webpayplus.responses.WebpayPlusTransactionCommitResponse;
import cl.transbank.webpay.webpayplus.responses.WebpayPlusTransactionCreateResponse;
import cl.transbank.webpay.webpayplus.responses.WebpayPlusTransactionRefundResponse;
import cl.transbank.webpay.webpayplus.responses.WebpayPlusTransactionStatusResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;

public class WebpayPlusTest extends TestBase {

    private static String apiUrl = ApiConstants.WEBPAY_ENDPOINT;
    private static Options option = new WebpayOptions(IntegrationCommerceCodes.WEBPAY_PLUS,
            IntegrationApiKeys.WEBPAY, IntegrationType.SERVER_MOCK);
    private static String vci = "TSY";
    private static double amount = 1000d;
    private static String status = "AUTHORIZED";
    private static String buyOrder = "1643997337";
    private static String sessionId = "1134425622";
    private static String cardNumber = "6623";
    private static String accountingDate = "0731";
    private static String transactionDate = "2021-07-31T23:31:14.249Z";
    private static String authorizationCode = "1213";
    private static String paymentTypeCode = "VN";
    private static byte responseCode = 0;
    private static double installmentsAmount;
    private static byte installmentsNumber = 0;
    private static double balance;

    private static String testToken = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";

    @BeforeAll
    public static void startProxy() {
        client = startClientAndServer(8888);
    }

    @AfterAll
    public static void stopProxy() {
        client.stop();
    }

    @Test
    public void create() throws IOException, TransactionCreateException {

        
        //WebpayPlus.configureForTesting();
        String url = String.format("/%s/transactions", apiUrl);

        String urlResponse = "https://webpay3gint.transbank.cl/webpayserver/initTransaction";
        Map<String, Object> mapResponse = new HashMap<String, Object>();
        mapResponse.put(ApiConstants.TOKEN_TEXT, testToken);
        mapResponse.put("url", urlResponse);

        Gson gson = new GsonBuilder().create();
        String jsonResponse = gson.toJson(mapResponse);
        setResponsePost(url, jsonResponse);

        String buyOrder = String.valueOf(new Random().nextInt(Integer.MAX_VALUE));
        String sessionId = String.valueOf(new Random().nextInt(Integer.MAX_VALUE));
        double amount = 1000;
        String returnUrl = "http://wwww.google.com";

        final WebpayPlusTransactionCreateResponse response = (new WebpayPlus.Transaction(option)).create(buyOrder, sessionId, amount, returnUrl);
        assertEquals(testToken, response.getToken());
        assertEquals(urlResponse, response.getUrl());
    }

    private Map<String, Object> generateCommitJsonResponse(){

        Map<String, String> mapResponseDetail = new HashMap<String, String>();
        mapResponseDetail.put("card_number", cardNumber);

        Map<String, Object> mapResponse = new HashMap<String, Object>();
        mapResponse.put("vci", vci);
        mapResponse.put("amount", amount);
        mapResponse.put("status", status);
        mapResponse.put("buy_order", buyOrder);
        mapResponse.put("session_id", sessionId);
        mapResponse.put("card_detail", mapResponseDetail);
        mapResponse.put("accounting_date", accountingDate);
        mapResponse.put("transaction_date", transactionDate);
        mapResponse.put("authorization_code", authorizationCode);
        mapResponse.put("payment_type_code", paymentTypeCode);
        mapResponse.put("response_code", responseCode);
        mapResponse.put("installments_number", installmentsNumber);

        return mapResponse;
    }

    @Test
    public void commit() throws IOException, TransactionCommitException {
        
        String url = String.format("/%s/transactions/%s", apiUrl, testToken);

        Map<String, Object> mapResponse = generateCommitJsonResponse();
        Gson gson = new GsonBuilder().create();
        setResponsePut(url, gson.toJson(mapResponse));

        final WebpayPlusTransactionCommitResponse response = (new WebpayPlus.Transaction(option)).commit(testToken);
        assertEquals(vci, response.getVci());
        assertEquals(amount, response.getAmount());
        assertEquals(status, response.getStatus());
        assertEquals(buyOrder, response.getBuyOrder());
        assertEquals(sessionId, response.getSessionId());
        assertEquals(cardNumber, response.getCardDetail().getCardNumber());
        assertEquals(accountingDate, response.getAccountingDate());
        assertEquals(transactionDate, response.getTransactionDate());
        assertEquals(authorizationCode, response.getAuthorizationCode());
        assertEquals(paymentTypeCode, response.getPaymentTypeCode());
        assertEquals(responseCode, response.getResponseCode());
        //assertEquals(response.getInstallmentsAmount(), mapResponse.get("amount"));
        assertEquals(installmentsNumber, response.getInstallmentsNumber());
    }


    @Test
    public void refund() throws IOException, TransactionRefundException {
        
        String url = String.format("/%s/transactions/%s/refunds", apiUrl, testToken);

        double amount = 1000d;
        String type = "REVERSED";

        Map<String, Object> mapResponse = new HashMap<String, Object>();
        mapResponse.put("type", type);

        Gson gson = new GsonBuilder().create();
        String jsonResponse = gson.toJson(mapResponse);
        setResponsePost(url, jsonResponse);

        final WebpayPlusTransactionRefundResponse response = (new WebpayPlus.Transaction(option)).refund(testToken, amount);
        assertEquals(type, response.getType());

    }

    @Test
    public void status() throws IOException, TransactionStatusException {
        
        String url = String.format("/%s/transactions/%s", apiUrl, testToken);

        Map<String, Object> mapResponse = generateCommitJsonResponse();
        Gson gson = new GsonBuilder().create();
        setResponseGet(url, gson.toJson(mapResponse));

        final WebpayPlusTransactionStatusResponse response = (new WebpayPlus.Transaction(option)).status(testToken);
        assertEquals(vci, response.getVci());
        assertEquals(amount, response.getAmount());
        assertEquals(status, response.getStatus());
        assertEquals(buyOrder, response.getBuyOrder());
        assertEquals(sessionId, response.getSessionId());
        assertEquals(cardNumber, response.getCardDetail().getCardNumber());
        assertEquals(accountingDate, response.getAccountingDate());
        assertEquals(transactionDate, response.getTransactionDate());
        assertEquals(authorizationCode, response.getAuthorizationCode());
        assertEquals(paymentTypeCode, response.getPaymentTypeCode());
        assertEquals(responseCode, response.getResponseCode());
        //assertEquals(response.getInstallmentsAmount(), mapResponse.get("amount"));
        assertEquals(installmentsNumber, response.getInstallmentsNumber());

    }

    @Test
    public void prueba1() throws IOException, TransactionStatusException {

        WebpayPlusTransactionStatusResponse r = new WebpayPlusTransactionCommitResponse();
        r.setVci(vci);
        r.setAmount(amount);
        r.setStatus(status);
        r.setBuyOrder(buyOrder);
        r.setSessionId(sessionId);
        r.setCardDetail(new CardDetail());
        r.getCardDetail().setCardNumber(cardNumber);


        WebpayPlus.Transaction tx = Mockito.mock(WebpayPlus.Transaction.class);
        Mockito.when(tx.status(testToken)).thenReturn(r);

        final WebpayPlusTransactionStatusResponse response = tx.status(testToken);
        assertEquals(vci, response.getVci());
        assertEquals(amount, response.getAmount());
        assertEquals(status, response.getStatus());
        assertEquals(buyOrder, response.getBuyOrder());
        assertEquals(sessionId, response.getSessionId());
        assertEquals(cardNumber, response.getCardDetail().getCardNumber());

    }



}
