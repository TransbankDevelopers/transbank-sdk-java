package webpayplus;

import cl.transbank.common.ApiConstants;
import cl.transbank.common.IntegrationApiKeys;
import cl.transbank.common.IntegrationCommerceCodes;
import cl.transbank.common.IntegrationType;
import cl.transbank.model.Options;
import cl.transbank.webpay.common.WebpayOptions;
import cl.transbank.webpay.exception.*;
import cl.transbank.webpay.webpayplus.WebpayPlus;
import cl.transbank.webpay.webpayplus.responses.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;

public class WebpayPlusDeferredTest extends WebpayPlusTestBase {
    private static String apiUrl = ApiConstants.WEBPAY_ENDPOINT;
    private static Options option = new WebpayOptions(IntegrationCommerceCodes.WEBPAY_PLUS_DEFERRED,
            IntegrationApiKeys.WEBPAY, IntegrationType.SERVER_MOCK);
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

    @Test
    public void commit() throws IOException, TransactionCommitException {
        WebpayPlusTransactionStatusResponse expectedResponse = generateStatusResponse();
        String url = String.format("/%s/transactions/%s", apiUrl, testToken);
        setResponsePut(url, generateCommitJsonResponse());

        final WebpayPlusTransactionCommitResponse response = (new WebpayPlus.Transaction(option)).commit(testToken);
        assertEquals(expectedResponse.getVci(), response.getVci());
        assertEquals(expectedResponse.getAmount(), response.getAmount());
        assertEquals(expectedResponse.getStatus(), response.getStatus());
        assertEquals(expectedResponse.getBuyOrder(), response.getBuyOrder());
        assertEquals(expectedResponse.getSessionId(), response.getSessionId());
        assertEquals(expectedResponse.getCardDetail().getCardNumber(), response.getCardDetail().getCardNumber());
        assertEquals(expectedResponse.getAccountingDate(), response.getAccountingDate());
        assertEquals(expectedResponse.getTransactionDate(), response.getTransactionDate());
        assertEquals(expectedResponse.getAuthorizationCode(), response.getAuthorizationCode());
        assertEquals(expectedResponse.getPaymentTypeCode(), response.getPaymentTypeCode());
        assertEquals(expectedResponse.getResponseCode(), response.getResponseCode());
        assertEquals(expectedResponse.getInstallmentsNumber(), response.getInstallmentsNumber());
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
        WebpayPlusTransactionStatusResponse expectedResponse = generateStatusResponse();
        String url = String.format("/%s/transactions/%s", apiUrl, testToken);
        setResponseGet(url, generateCommitJsonResponse());

        final WebpayPlusTransactionStatusResponse response = (new WebpayPlus.Transaction(option)).status(testToken);
        assertEquals(expectedResponse.getVci(), response.getVci());
        assertEquals(expectedResponse.getAmount(), response.getAmount());
        assertEquals(expectedResponse.getStatus(), response.getStatus());
        assertEquals(expectedResponse.getBuyOrder(), response.getBuyOrder());
        assertEquals(expectedResponse.getSessionId(), response.getSessionId());
        assertEquals(expectedResponse.getCardDetail().getCardNumber(), response.getCardDetail().getCardNumber());
        assertEquals(expectedResponse.getAccountingDate(), response.getAccountingDate());
        assertEquals(expectedResponse.getTransactionDate(), response.getTransactionDate());
        assertEquals(expectedResponse.getAuthorizationCode(), response.getAuthorizationCode());
        assertEquals(expectedResponse.getPaymentTypeCode(), response.getPaymentTypeCode());
        assertEquals(expectedResponse.getResponseCode(), response.getResponseCode());
        assertEquals(expectedResponse.getInstallmentsNumber(), response.getInstallmentsNumber());
    }

    @Test
    public void capture() throws IOException, TransactionCaptureException {
        String url = String.format("/%s/transactions/%s/capture", apiUrl, testToken);
        String authorizationCode = "1213";
        String authorizationDate = "2021-08-01T03:17:42.785Z";
        double capturedAmount = 1000.0;
        byte responseCode = 0;

        Map<String, Object> mapResponse = new HashMap<String, Object>();
        mapResponse.put("authorization_code", authorizationCode);
        mapResponse.put("authorization_date", authorizationDate);
        mapResponse.put("captured_amount", capturedAmount);
        mapResponse.put("response_code", responseCode);

        Gson gson = new GsonBuilder().create();
        String jsonResponse = gson.toJson(mapResponse);
        setResponsePut(url, jsonResponse);

        String buyOrder = String.valueOf(new Random().nextInt(Integer.MAX_VALUE));
        String authorization = "1213";
        double amount = 1000;

        final WebpayPlusTransactionCaptureResponse response = (new WebpayPlus.Transaction(option)).capture(testToken, buyOrder, authorization, amount);
        assertEquals(authorizationCode, response.getAuthorizationCode());
        assertEquals(authorizationDate, response.getAuthorizationDate());
        assertEquals(capturedAmount, response.getCapturedAmount());
        assertEquals(responseCode, response.getResponseCode());
    }

}
