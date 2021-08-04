package webpayplus;

import cl.transbank.common.IntegrationType;
import cl.transbank.webpay.exception.*;
import cl.transbank.webpay.webpayplus.WebpayPlus;
import cl.transbank.webpay.webpayplus.model.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockserver.client.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.junit.jupiter.MockServerExtension;
import org.mockserver.junit.jupiter.MockServerSettings;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockServerExtension.class)
@MockServerSettings(ports = {8888})
public class WebPayPlusDeferredTest extends TestBase {

    public WebPayPlusDeferredTest(MockServerClient client) {
        this.client = client;
    }

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

    @Test
    public void create() throws IOException, TransactionCreateException {
        WebpayPlus.DeferredTransaction.setIntegrationType(IntegrationType.SERVER_MOCK);
        String token = "01ab33cb02f389be7e912ca33d459fab7ee76e8d34116a75d946076fc1ec1cd2";
        String url = "/rswebpaytransaction/api/webpay/v1.0/transactions";

        String urlResponse = "https://webpay3gint.transbank.cl/webpayserver/initTransaction";
        Map<String, Object> mapResponse = new HashMap<String, Object>();
        mapResponse.put("token", token);
        mapResponse.put("url", urlResponse);

        Gson gson = new GsonBuilder().create();
        String jsonResponse = gson.toJson(mapResponse);
        setResponsePost(url, jsonResponse);

        String buyOrder = String.valueOf(new Random().nextInt(Integer.MAX_VALUE));
        String sessionId = String.valueOf(new Random().nextInt(Integer.MAX_VALUE));
        double amount = 1000;
        String returnUrl = "http://wwww.google.com";

        final WebpayPlusTransactionCreateResponse response = WebpayPlus.DeferredTransaction.create(buyOrder, sessionId, amount, returnUrl);
        assertEquals(response.getToken(), token);
        assertEquals(response.getUrl(), urlResponse);
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
        WebpayPlus.DeferredTransaction.setIntegrationType(IntegrationType.SERVER_MOCK);
        String token = "01abfa9e931185df8171bb1f98c1ceb65d67ca699e47b900ab8eb13851ca99fb";
        String url = String.format("/rswebpaytransaction/api/webpay/v1.0/transactions/%s",token);

        String vci = "TSY";
        double amount = 1000d;
        String status = "AUTHORIZED";
        String buyOrder = "1643997337";
        String sessionId = "1134425622";
        String cardNumber = "6623";
        String accountingDate = "0731";
        String transactionDate = "2021-07-31T23:31:14.249Z";
        String authorizationCode = "1213";
        String paymentTypeCode = "VN";
        byte responseCode = 0;
        double installmentsAmount;
        byte installmentsNumber = 0;
        double balance;

        Map<String, Object> mapResponse = generateCommitJsonResponse();
        Gson gson = new GsonBuilder().create();
        setResponsePut(url, gson.toJson(mapResponse));

        //System.out.println("jsonResponse: " + jsonResponse);
        //System.out.println("url: " + url);

        final WebpayPlusTransactionCommitResponse response = WebpayPlus.DeferredTransaction.commit(token);
        assertEquals(response.getVci(), vci);
        assertEquals(response.getAmount(), amount);
        assertEquals(response.getStatus(), status);
        assertEquals(response.getBuyOrder(), buyOrder);
        assertEquals(response.getSessionId(), sessionId);
        assertEquals(response.getCardDetail().getCardNumber(), cardNumber);
        assertEquals(response.getAccountingDate(), accountingDate);
        assertEquals(response.getTransactionDate(), transactionDate);
        assertEquals(response.getAuthorizationCode(), authorizationCode);
        assertEquals(response.getPaymentTypeCode(), paymentTypeCode);
        assertEquals(response.getResponseCode(), responseCode);
        //assertEquals(response.getInstallmentsAmount(), mapResponse.get("amount"));
        assertEquals(response.getInstallmentsNumber(), installmentsNumber);
        //assertEquals(response.getBalance(), mapResponse.get("amount"));
    }


    @Test
    public void refund() throws IOException, TransactionRefundException {
        WebpayPlus.DeferredTransaction.setIntegrationType(IntegrationType.SERVER_MOCK);
        String token = "01abfa9e931185df8171bb1f98c1ceb65d67ca699e47b900ab8eb13851ca99fb";
        String url = String.format("/rswebpaytransaction/api/webpay/v1.0/transactions/%s/refunds",token);

        double amount = 1000d;
        String type = "REVERSED";

        Map<String, Object> mapResponse = new HashMap<String, Object>();
        mapResponse.put("type", type);

        Gson gson = new GsonBuilder().create();
        String jsonResponse = gson.toJson(mapResponse);
        setResponsePost(url, jsonResponse);

        final WebpayPlusTransactionRefundResponse response = WebpayPlus.DeferredTransaction.refund(token, amount);
        assertEquals(response.getType(), type);

    }

    @Test
    public void status() throws IOException, TransactionStatusException {
        WebpayPlus.DeferredTransaction.setIntegrationType(IntegrationType.SERVER_MOCK);
        String token = "01abfa9e931185df8171bb1f98c1ceb65d67ca699e47b900ab8eb13851ca99fb";
        String url = String.format("/rswebpaytransaction/api/webpay/v1.0/transactions/%s",token);

        Map<String, Object> mapResponse = generateCommitJsonResponse();
        Gson gson = new GsonBuilder().create();
        setResponseGet(url, gson.toJson(mapResponse));

        final WebpayPlusTransactionStatusResponse response = WebpayPlus.DeferredTransaction.status(token);
        assertEquals(response.getVci(), vci);
        assertEquals(response.getAmount(), amount);
        assertEquals(response.getStatus(), status);
        assertEquals(response.getBuyOrder(), buyOrder);
        assertEquals(response.getSessionId(), sessionId);
        assertEquals(response.getCardDetail().getCardNumber(), cardNumber);
        assertEquals(response.getAccountingDate(), accountingDate);
        assertEquals(response.getTransactionDate(), transactionDate);
        assertEquals(response.getAuthorizationCode(), authorizationCode);
        assertEquals(response.getPaymentTypeCode(), paymentTypeCode);
        assertEquals(response.getResponseCode(), responseCode);
        //assertEquals(response.getInstallmentsAmount(), mapResponse.get("amount"));
        assertEquals(response.getInstallmentsNumber(), installmentsNumber);

    }

    @Test
    public void capture() throws IOException, TransactionCaptureException {
        WebpayPlus.DeferredTransaction.setIntegrationType(IntegrationType.SERVER_MOCK);
        String token = "01ab33cb02f389be7e912ca33d459fab7ee76e8d34116a75d946076fc1ec1cd2";
        String url = String.format("/rswebpaytransaction/api/webpay/v1.0/transactions/%s/capture",token);

        String authorizationCode = "138248";
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
        String authorization = String.valueOf(new Random().nextInt(Integer.MAX_VALUE));
        double amount = 1000;

        final WebpayPlusTransactionCaptureResponse response = WebpayPlus.DeferredTransaction.capture(token, buyOrder, authorization, amount);
        assertEquals(response.getAuthorizationCode(), authorizationCode);
        assertEquals(response.getAuthorizationDate(), authorizationDate);
        assertEquals(response.getCapturedAmount(), capturedAmount);
        assertEquals(response.getResponseCode(), responseCode);
    }

}
