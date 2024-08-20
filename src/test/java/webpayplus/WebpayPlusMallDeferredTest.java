package webpayplus;

import cl.transbank.common.ApiConstants;
import cl.transbank.common.IntegrationApiKeys;
import cl.transbank.common.IntegrationCommerceCodes;
import cl.transbank.common.IntegrationType;
import cl.transbank.model.MallTransactionCreateDetails;
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
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;

public class WebpayPlusMallDeferredTest extends TestBase {

    private static String apiUrl = ApiConstants.WEBPAY_ENDPOINT;
    private static Options option = new WebpayOptions(IntegrationCommerceCodes.WEBPAY_PLUS_MALL_DEFERRED,
            IntegrationApiKeys.WEBPAY, IntegrationType.SERVER_MOCK);
    private static String vci = "TSY";
    private static String buyOrder = "1759488117";
    private static String sessionId = "777265108";
    private static String cardNumber = "6623";
    private static String accountingDate = "0801";
    private static String transactionDate = "2021-08-01T04:14:09.053Z";

    //details 1
    private static double amount1 = 1000d;
    private static String status1 = "AUTHORIZED";
    private static String authorizationCode1 = "1213";
    private static String paymentTypeCode1 = "VN";
    private static byte responseCode1 = 0;
    private static double installmentsAmount1;
    private static byte installmentsNumber1 = 0;
    private static String commerceCode1 = "597055555536";
    private static String buyOrder1 = "500894028";
    //details 2
    private static double amount2 = 1000d;
    private static String status2 = "AUTHORIZED";
    private static String authorizationCode2 = "1213";
    private static String paymentTypeCode2 = "VN";
    private static byte responseCode2 = 0;
    private static double installmentsAmount2;
    private static byte installmentsNumber2 = 0;
    private static String commerceCode2 = "597055555537";
    private static String buyOrder2 = "1936357040";

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
        String returnUrl = "http://wwww.google.com";

        String buyOrder3 = String.valueOf(new Random().nextInt(Integer.MAX_VALUE));
        String sessionId3 = String.valueOf(new Random().nextInt(Integer.MAX_VALUE));

        String buyOrderMallOne = String.valueOf(new Random().nextInt(Integer.MAX_VALUE));
        double amountMallOne = 1000;
        String mallOneCommerceCode = "597055555536";

        String buyOrderMallTwo = String.valueOf(new Random().nextInt(Integer.MAX_VALUE));
        double amountMallTwo = 1000;
        String mallTwoCommerceCode = "597055555537";

        final MallTransactionCreateDetails mallDetails = MallTransactionCreateDetails.build()
                .add(amountMallOne, mallOneCommerceCode, buyOrderMallOne)
                .add(amountMallTwo, mallTwoCommerceCode, buyOrderMallTwo);

        final WebpayPlusMallTransactionCreateResponse response = (new WebpayPlus.MallTransaction(option)).create(buyOrder3,sessionId3, returnUrl, mallDetails);
        assertEquals(response.getToken(), testToken);
        assertEquals(response.getUrl(), urlResponse);
    }

    private Map<String, Object> generateCommitJsonResponse(){

        Map<String, String> mapResponseCardDetail = new HashMap<String, String>();
        mapResponseCardDetail.put("card_number", cardNumber);

        Map<String, Object> mapResponseDetail1 = new HashMap<String, Object>();
        mapResponseDetail1.put("amount", amount1);
        mapResponseDetail1.put("status", status1);
        mapResponseDetail1.put("authorization_code", authorizationCode1);
        mapResponseDetail1.put("payment_type_code", paymentTypeCode1);
        mapResponseDetail1.put("response_code", responseCode1);
        mapResponseDetail1.put("installments_number", installmentsNumber1);
        mapResponseDetail1.put("commerce_code", commerceCode1);
        mapResponseDetail1.put("buy_order", buyOrder1);

        Map<String, Object> mapResponseDetail2 = new HashMap<String, Object>();
        mapResponseDetail2.put("amount", amount2);
        mapResponseDetail2.put("status", status2);
        mapResponseDetail2.put("authorization_code", authorizationCode2);
        mapResponseDetail2.put("payment_type_code", paymentTypeCode2);
        mapResponseDetail2.put("response_code", responseCode2);
        mapResponseDetail2.put("installments_number", installmentsNumber2);
        mapResponseDetail2.put("commerce_code", commerceCode2);
        mapResponseDetail2.put("buy_order", buyOrder2);

        Map<String, Object> mapResponse = new HashMap<String, Object>();
        mapResponse.put("vci", vci);
        mapResponse.put("buy_order", buyOrder);
        mapResponse.put("session_id", sessionId);
        mapResponse.put("card_detail", mapResponseCardDetail);
        mapResponse.put("accounting_date", accountingDate);
        mapResponse.put("transaction_date", transactionDate);
        //details
        List<Map<String, Object>> lstDetail = new ArrayList<Map<String, Object>>();
        lstDetail.add(mapResponseDetail1);
        lstDetail.add(mapResponseDetail2);
        mapResponse.put("details", lstDetail);

        return mapResponse;
    }

    @Test
    public void commit() throws IOException, TransactionCommitException {
        String url = String.format("/%s/transactions/%s", apiUrl, testToken);

        Map<String, Object> mapResponse = generateCommitJsonResponse();
        Gson gson = new GsonBuilder().create();
        setResponsePut(url, gson.toJson(mapResponse));

        final WebpayPlusMallTransactionCommitResponse response = (new WebpayPlus.MallTransaction(option)).commit(testToken);

        assertEquals(response.getVci(), vci);
        assertEquals(response.getBuyOrder(), buyOrder);
        assertEquals(response.getSessionId(), sessionId);
        assertEquals(response.getCardDetail().getCardNumber(), cardNumber);
        assertEquals(response.getAccountingDate(), accountingDate);
        assertEquals(response.getTransactionDate(), transactionDate);
        //details1
        assertEquals(response.getDetails().get(0).getAmount(), amount1);
        assertEquals(response.getDetails().get(0).getStatus(), status1);
        assertEquals(response.getDetails().get(0).getAuthorizationCode(), authorizationCode1);
        assertEquals(response.getDetails().get(0).getPaymentTypeCode(), paymentTypeCode1);
        assertEquals(response.getDetails().get(0).getResponseCode(), responseCode1);
        assertEquals(response.getDetails().get(0).getInstallmentsNumber(), installmentsNumber1);
        assertEquals(response.getDetails().get(0).getCommerceCode(), commerceCode1);
        assertEquals(response.getDetails().get(0).getBuyOrder(), buyOrder1);
        //details2
        assertEquals(response.getDetails().get(1).getAmount(), amount2);
        assertEquals(response.getDetails().get(1).getStatus(), status2);
        assertEquals(response.getDetails().get(1).getAuthorizationCode(), authorizationCode2);
        assertEquals(response.getDetails().get(1).getPaymentTypeCode(), paymentTypeCode2);
        assertEquals(response.getDetails().get(1).getResponseCode(), responseCode2);
        assertEquals(response.getDetails().get(1).getInstallmentsNumber(), installmentsNumber2);
        assertEquals(response.getDetails().get(1).getCommerceCode(), commerceCode2);
        assertEquals(response.getDetails().get(1).getBuyOrder(), buyOrder2);
    }


    @Test
    public void refund() throws IOException, TransactionRefundException {
        String url = String.format("/%s/transactions/%s/refunds", apiUrl, testToken);
        String type = "REVERSED";

        Map<String, Object> mapResponse = new HashMap<String, Object>();
        mapResponse.put("type", type);

        Gson gson = new GsonBuilder().create();
        String jsonResponse = gson.toJson(mapResponse);
        setResponsePost(url, jsonResponse);

        String childBuyOrder = "500894028";
        String childCommerceCode = "597055555536";
        double amount = 1000d;

        final WebpayPlusMallTransactionRefundResponse response = (new WebpayPlus.MallTransaction(option)).refund(testToken, childBuyOrder, childCommerceCode, amount);
        assertEquals(response.getType(), type);

    }

    @Test
    public void status() throws IOException, TransactionStatusException {
        String url = String.format("/%s/transactions/%s", apiUrl, testToken);

        Map<String, Object> mapResponse = generateCommitJsonResponse();
        Gson gson = new GsonBuilder().create();
        setResponseGet(url, gson.toJson(mapResponse));

        final WebpayPlusMallTransactionStatusResponse response = (new WebpayPlus.MallTransaction(option)).status(testToken);

        assertEquals(response.getVci(), vci);
        assertEquals(response.getBuyOrder(), buyOrder);
        assertEquals(response.getSessionId(), sessionId);
        assertEquals(response.getCardDetail().getCardNumber(), cardNumber);
        assertEquals(response.getAccountingDate(), accountingDate);
        assertEquals(response.getTransactionDate(), transactionDate);
        //details1
        assertEquals(response.getDetails().get(0).getAmount(), amount1);
        assertEquals(response.getDetails().get(0).getStatus(), status1);
        assertEquals(response.getDetails().get(0).getAuthorizationCode(), authorizationCode1);
        assertEquals(response.getDetails().get(0).getPaymentTypeCode(), paymentTypeCode1);
        assertEquals(response.getDetails().get(0).getResponseCode(), responseCode1);
        assertEquals(response.getDetails().get(0).getInstallmentsNumber(), installmentsNumber1);
        assertEquals(response.getDetails().get(0).getCommerceCode(), commerceCode1);
        assertEquals(response.getDetails().get(0).getBuyOrder(), buyOrder1);
        //details2
        assertEquals(response.getDetails().get(1).getAmount(), amount2);
        assertEquals(response.getDetails().get(1).getStatus(), status2);
        assertEquals(response.getDetails().get(1).getAuthorizationCode(), authorizationCode2);
        assertEquals(response.getDetails().get(1).getPaymentTypeCode(), paymentTypeCode2);
        assertEquals(response.getDetails().get(1).getResponseCode(), responseCode2);
        assertEquals(response.getDetails().get(1).getInstallmentsNumber(), installmentsNumber2);
        assertEquals(response.getDetails().get(1).getCommerceCode(), commerceCode2);
        assertEquals(response.getDetails().get(1).getBuyOrder(), buyOrder2);
    }


    @Test
    public void capture() throws IOException, TransactionCaptureException {
        String url = String.format("/%s/transactions/%s/capture", apiUrl, testToken);

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

        final WebpayPlusMallTransactionCaptureResponse response = (new WebpayPlus.MallTransaction(option)).capture(commerceCode1, testToken, buyOrder1, authorizationCode1, amount1);
        assertEquals(response.getAuthorizationCode(), authorizationCode);
        assertEquals(response.getAuthorizationDate(), authorizationDate);
        assertEquals(response.getCapturedAmount(), capturedAmount);
        assertEquals(response.getResponseCode(), responseCode);
    }
}
