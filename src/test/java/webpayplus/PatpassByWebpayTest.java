package webpayplus;

import cl.transbank.common.IntegrationType;
import cl.transbank.patpass.PatpassByWebpay;
import cl.transbank.patpass.model.PatpassByWebpayTransactionCommitResponse;
import cl.transbank.patpass.model.PatpassByWebpayTransactionCreateResponse;
import cl.transbank.patpass.model.PatpassByWebpayTransactionRefundResponse;
import cl.transbank.patpass.model.PatpassByWebpayTransactionStatusResponse;
import cl.transbank.webpay.exception.TransactionCommitException;
import cl.transbank.webpay.exception.TransactionCreateException;
import cl.transbank.webpay.exception.TransactionRefundException;
import cl.transbank.webpay.exception.TransactionStatusException;
import cl.transbank.webpay.webpayplus.WebpayPlus;
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


public class PatpassByWebpayTest  extends TestBase {

    private static String apiUrl = "/rswebpaytransaction/api/webpay/v1.2";

    private static String buyOrder = String.valueOf(new Random().nextInt(Integer.MAX_VALUE));
    private static String sessionId = String.valueOf(new Random().nextInt(Integer.MAX_VALUE));
    private static String serviceId = nextString(20);
    private static String cardHolderId = nextString(20);
    private static String cardHolderName = nextString(20);
    private static String cardHolderLastName1 = nextString(20);
    private static String cardHolderLastName2 = nextString(20);
    private static String cardHolderMail = String.format("%s@%s.COM", nextString(10), nextString(7));
    private static String cellphoneNumber = String.valueOf(new Random().nextInt(Integer.MAX_VALUE));
    private static String expirationDate = "2222-11-11";
    private static String commerceMail = String.format("%s@%s.COM", nextString(10), nextString(7));

    private static String vci = "TSY";
    private static double amount = 1000d;
    private static String status = "AUTHORIZED";
    private static String buyOrderResp = "1061524924";
    private static String sessionIdResp = "1331417336";
    private static String cardNumber = "6623";
    private static String accountingDate = "0802";
    private static String transactionDate = "2021-08-02T20:59:21.994Z";
    private static String authorizationCode = "1213";
    private static String paymentTypeCode = "VN";
    private static byte responseCode = 0;
    private static byte installmentsNumber = 0;

    @BeforeAll
    public static void startProxy() {
        client = startClientAndServer(8888);
    }

    @AfterAll
    public static void stopProxy() {
        client.stop();
    }

    @Test
    public void start() throws IOException, TransactionCreateException {
        PatpassByWebpay.setIntegrationType(IntegrationType.SERVER_MOCK);
        String token = "01ab1cfcdd0787257c340944b2aa50339c400901c43a4e652caf769b80c303e9";
        String url = String.format("%s/transactions",apiUrl);

        String urlResponse = "https://webpay3gint.transbank.cl/webpayserver/initTransaction";
        Map<String, Object> mapResponse = new HashMap<String, Object>();
        mapResponse.put("token", token);
        mapResponse.put("url", urlResponse);

        Gson gson = new GsonBuilder().create();
        String jsonResponse = gson.toJson(mapResponse);
        setResponsePost(url, jsonResponse);

        String returnUrl = "http://localhost:8081/patpass-webpay/commit";

        PatpassByWebpayTransactionCreateResponse response = PatpassByWebpay.Transaction.create(buyOrder, sessionId, 1000, returnUrl, serviceId, cardHolderId, cardHolderName,
                cardHolderLastName1, cardHolderLastName2, cardHolderMail, cellphoneNumber, expirationDate, commerceMail, false, null);

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
        mapResponse.put("buy_order", buyOrderResp);
        mapResponse.put("session_id", sessionIdResp);
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
        PatpassByWebpay.setIntegrationType(IntegrationType.SERVER_MOCK);
        String token = "01ab1cfcdd0787257c340944b2aa50339c400901c43a4e652caf769b80c303e9";
        String url = String.format("%s/transactions/%s", apiUrl, token);

        Map<String, Object> mapResponse = generateCommitJsonResponse();
        Gson gson = new GsonBuilder().create();
        setResponsePut(url, gson.toJson(mapResponse));

        final PatpassByWebpayTransactionCommitResponse response = PatpassByWebpay.Transaction.commit(token);

        assertEquals(response.getVci(), vci);
        assertEquals(response.getAmount(), amount);
        assertEquals(response.getStatus(), status);
        assertEquals(response.getBuyOrder(), buyOrderResp);
        assertEquals(response.getSessionId(), sessionIdResp);
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
    public void status() throws IOException, TransactionStatusException {
        PatpassByWebpay.setIntegrationType(IntegrationType.SERVER_MOCK);
        String token = "01ab1cfcdd0787257c340944b2aa50339c400901c43a4e652caf769b80c303e9";
        String url = String.format("%s/transactions/%s", apiUrl, token);

        Map<String, Object> mapResponse = generateCommitJsonResponse();
        Gson gson = new GsonBuilder().create();
        setResponseGet(url, gson.toJson(mapResponse));

        final PatpassByWebpayTransactionStatusResponse response = PatpassByWebpay.Transaction.status(token);

        assertEquals(response.getVci(), vci);
        assertEquals(response.getAmount(), amount);
        assertEquals(response.getStatus(), status);
        assertEquals(response.getBuyOrder(), buyOrderResp);
        assertEquals(response.getSessionId(), sessionIdResp);
        assertEquals(response.getCardDetail().getCardNumber(), cardNumber);
        assertEquals(response.getAccountingDate(), accountingDate);
        assertEquals(response.getTransactionDate(), transactionDate);
        assertEquals(response.getAuthorizationCode(), authorizationCode);
        assertEquals(response.getPaymentTypeCode(), paymentTypeCode);
        //assertEquals(response.getInstallmentsAmount(), mapResponse.get("amount"));
        assertEquals(response.getInstallmentsNumber(), installmentsNumber);
    }

    @Test
    public void refund() throws IOException, TransactionRefundException {
        WebpayPlus.Transaction.setIntegrationType(IntegrationType.SERVER_MOCK);
        String token = "01abfa9e931185df8171bb1f98c1ceb65d67ca699e47b900ab8eb13851ca99fb";
        String url = String.format("%s/transactions/%s/refunds", apiUrl, token);

        double amount = 1000d;
        String type = "REVERSED";

        Map<String, Object> mapResponse = new HashMap<String, Object>();
        mapResponse.put("type", type);

        Gson gson = new GsonBuilder().create();
        String jsonResponse = gson.toJson(mapResponse);
        setResponsePost(url, jsonResponse);

        final PatpassByWebpayTransactionRefundResponse response = PatpassByWebpay.Transaction.refund(token, amount);
        assertEquals(response.getType(), type);

    }

    static String nextString(int length) {
        char[] buf = new char[length];
        Random random = new Random();
        final char[] symbols = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        for (int idx = 0; idx < buf.length; ++idx)
            buf[idx] = symbols[random.nextInt(symbols.length)];
        return new String(buf);
    }

}





