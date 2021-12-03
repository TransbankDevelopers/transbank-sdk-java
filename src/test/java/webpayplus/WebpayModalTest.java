package webpayplus;

import cl.transbank.common.ApiConstants;
import cl.transbank.webpay.exception.*;
import cl.transbank.webpay.modal.WebpayPlusModal;
import cl.transbank.webpay.modal.responses.*;
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

public class WebpayModalTest extends TestBase {

    private static String apiUrl = ApiConstants.WEBPAY_ENDPOINT;

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

    private static String testToken = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";

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

        WebpayPlusModal.configureForMock();
        String url = String.format("/%s/transactions", apiUrl);

        String urlResponse = "https://webpay3gint.transbank.cl/webpayserver/initTransaction";
        Map<String, Object> mapResponse = new HashMap<String, Object>();
        mapResponse.put("token", testToken);

        Gson gson = new GsonBuilder().create();
        String jsonResponse = gson.toJson(mapResponse);
        setResponsePost(url, jsonResponse);

        String buyOrder = String.valueOf(new Random().nextInt(Integer.MAX_VALUE));
        String sessionId = String.valueOf(new Random().nextInt(Integer.MAX_VALUE));
        double amount = 1000;
        String returnUrl = "http://wwww.google.com";

        final ModalTransactionCreateResponse response = (new WebpayPlusModal.Transaction()).create(buyOrder, sessionId, amount);
        assertEquals(testToken, response.getToken());
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
        WebpayPlusModal.configureForMock();
        String url = String.format("/%s/transactions/%s", apiUrl, testToken);

        Map<String, Object> mapResponse = generateCommitJsonResponse();
        Gson gson = new GsonBuilder().create();
        setResponsePut(url, gson.toJson(mapResponse));

        final ModalTransactionCommitResponse response = (new WebpayPlusModal.Transaction()).commit(testToken);
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
        WebpayPlusModal.configureForMock();
        String url = String.format("/%s/transactions/%s/refunds", apiUrl, testToken);

        double amount = 1000d;
        String type = "REVERSED";

        Map<String, Object> mapResponse = new HashMap<String, Object>();
        mapResponse.put("type", type);

        Gson gson = new GsonBuilder().create();
        String jsonResponse = gson.toJson(mapResponse);
        setResponsePost(url, jsonResponse);

        final ModalTransactionRefundResponse response = (new WebpayPlusModal.Transaction()).refund(testToken, amount);
        assertEquals(type, response.getType());

    }

    @Test
    public void status() throws IOException, TransactionStatusException {
        WebpayPlusModal.configureForMock();
        String url = String.format("/%s/transactions/%s", apiUrl, testToken);

        Map<String, Object> mapResponse = generateCommitJsonResponse();
        Gson gson = new GsonBuilder().create();
        setResponseGet(url, gson.toJson(mapResponse));

        final ModalTransactionStatusResponse response = (new WebpayPlusModal.Transaction()).status(testToken);
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





}
