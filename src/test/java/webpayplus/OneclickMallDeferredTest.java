package webpayplus;

import cl.transbank.common.ApiConstants;
import cl.transbank.common.IntegrationApiKeys;
import cl.transbank.common.IntegrationCommerceCodes;
import cl.transbank.common.IntegrationType;
import cl.transbank.model.Options;
import cl.transbank.webpay.common.WebpayOptions;
import cl.transbank.webpay.exception.*;
import cl.transbank.webpay.oneclick.Oneclick;
import cl.transbank.webpay.oneclick.model.*;
import cl.transbank.webpay.oneclick.responses.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;


public class OneclickMallDeferredTest extends TestBase {

    private static String API_URL = ApiConstants.ONECLICK_ENDPOINT;
    private static Options OPTION = new WebpayOptions(IntegrationCommerceCodes.ONECLICK_MALL_DEFERRED,
            IntegrationApiKeys.WEBPAY, IntegrationType.SERVER_MOCK);
    private static String USERNAME = "goncafa";
    private static String EMAIL = "gonzalo.castillo@continuum.cl";
    private static String TBK_USER = "aaaaaaaaaaaaa-bbbbbbbb-cccccc";
    private static String CARD_NUMBER = "6623";
    private static String TRANSACTION_DATE = "2021-08-01T05:30:06.557Z";
    private static String ACCOUNTING_DATE = "0801";
    private static String BUY_ORDER = "724900565";
    private static double AMOUNT_1 = 1000d;
    private static String STATUS_1 = "AUTHORIZED";
    private static String AUTHORIZATION_CODE_1 = "1213";
    private static String PAYMENT_TYPE_CODE_1 = "VN";
    private static byte INSTALLMENTS_NUMBER_1 = 0;
    private static String COMMERCE_CODE_1 = "597055555542";
    private static String BUY_ORDER_1 = "2019439134";
    private static byte RESPONSE_CODE_1 = 0;
    private static double AMOUNT_2 = 1000d;
    private static String STATUS_2 = "AUTHORIZED";
    private static String AUTHORIZATION_CODE_2 = "1213";
    private static String PAYMENT_TYPE_CODE_2 = "VN";
    private static byte INSTALLMENTS_NUMBER_2 = 0;
    private static String COMMERCE_CODE_2 = "597055555543";
    private static String BUY_ORDER_2 = "353345213";
    private static byte RESPONSE_CODE_2 = 0;
    private static String TEST_TOKEN = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";

    @BeforeAll
    public static void startProxy() {
        client = startClientAndServer(8888);
    }

    @AfterAll
    public static void stopProxy() {
        client.stop();
    }

    @Test
    public void start() throws IOException, InscriptionStartException {
        String url = String.format("/%s/inscriptions",API_URL);

        String urlResponse = "https://webpay3gint.transbank.cl/webpayserver/bp_multicode_inscription.cgi";
        Map<String, Object> mapResponse = new HashMap<String, Object>();
        mapResponse.put(ApiConstants.TOKEN_TEXT, TEST_TOKEN);
        mapResponse.put("url_webpay", urlResponse);

        Gson gson = new GsonBuilder().create();
        String jsonResponse = gson.toJson(mapResponse);
        setResponsePost(url, jsonResponse);

        String returnUrl = "http://localhost:8081/oneclick-mall/finish";

        final OneclickMallInscriptionStartResponse response = (new Oneclick.MallInscription(OPTION)).start(USERNAME, EMAIL, returnUrl);
        assertEquals(response.getToken(), TEST_TOKEN);
        assertEquals(response.getUrlWebpay(), urlResponse);
    }


    @Test
    public void finish() throws IOException, InscriptionFinishException {
        String url = String.format("/%s/inscriptions/%s", API_URL, TEST_TOKEN);

        byte responseCode = 0;
        String authorizationCode = "1213";
        String cardType = "Visa";
        String cardNumber = "XXXXXXXXXXXX6623";

        Map<String, Object> mapResponse = new HashMap<String, Object>();
        mapResponse.put("response_code", responseCode);
        mapResponse.put("tbk_user", TBK_USER);
        mapResponse.put("authorization_code", authorizationCode);
        mapResponse.put("card_type", cardType);
        mapResponse.put("card_number", cardNumber);

        Gson gson = new GsonBuilder().create();
        String jsonResponse = gson.toJson(mapResponse);
        setResponsePut(url, jsonResponse);

        final OneclickMallInscriptionFinishResponse response = (new Oneclick.MallInscription(OPTION)).finish(TEST_TOKEN);
        assertEquals(response.getResponseCode(), responseCode);
        assertEquals(response.getTbkUser(), TBK_USER);
        assertEquals(response.getAuthorizationCode(), authorizationCode);
        assertEquals(response.getCardType(), cardType);
        assertEquals(response.getCardNumber(), cardNumber);

    }


     private Map<String, Object> generateAutorizeJsonResponse(){

        Map<String, String> mapResponseCardDetail = new HashMap<String, String>();
        mapResponseCardDetail.put("card_number", CARD_NUMBER);

        Map<String, Object> mapResponseDetail1 = new HashMap<String, Object>();
        mapResponseDetail1.put("amount", AMOUNT_1);
        mapResponseDetail1.put("status", STATUS_1);
        mapResponseDetail1.put("authorization_code", AUTHORIZATION_CODE_1);
        mapResponseDetail1.put("payment_type_code", PAYMENT_TYPE_CODE_1);
        mapResponseDetail1.put("response_code", RESPONSE_CODE_1);
        mapResponseDetail1.put("installments_number", INSTALLMENTS_NUMBER_1);
        mapResponseDetail1.put("commerce_code", COMMERCE_CODE_1);
        mapResponseDetail1.put("buy_order", BUY_ORDER_1);

        Map<String, Object> mapResponseDetail2 = new HashMap<String, Object>();
        mapResponseDetail2.put("amount", AMOUNT_2);
        mapResponseDetail2.put("status", STATUS_2);
        mapResponseDetail2.put("authorization_code", AUTHORIZATION_CODE_2);
        mapResponseDetail2.put("payment_type_code", PAYMENT_TYPE_CODE_2);
        mapResponseDetail2.put("response_code", RESPONSE_CODE_2);
        mapResponseDetail2.put("installments_number", INSTALLMENTS_NUMBER_2);
        mapResponseDetail2.put("commerce_code", COMMERCE_CODE_2);
        mapResponseDetail2.put("buy_order", BUY_ORDER_2);


        Map<String, Object> mapResponse = new HashMap<String, Object>();
        mapResponse.put("buy_order", BUY_ORDER);
        mapResponse.put("card_detail", mapResponseCardDetail);
        mapResponse.put("accounting_date", ACCOUNTING_DATE);
        mapResponse.put("transaction_date", TRANSACTION_DATE);
        //details
        List<Map<String, Object>> lstDetail = new ArrayList<Map<String, Object>>();
        lstDetail.add(mapResponseDetail1);
        lstDetail.add(mapResponseDetail2);
        mapResponse.put("details", lstDetail);

        return mapResponse;
    }

    @Test
    public void authorize() throws IOException, TransactionAuthorizeException {
        String url = String.format("/%s/transactions",API_URL);

        Map<String, Object> mapResponse = generateAutorizeJsonResponse();
        Gson gson = new GsonBuilder().create();
        setResponsePost(url, gson.toJson(mapResponse));

        String tbkUserReq = "aaaaaaaaaaaaa-bbbbbbbb-cccccc";
        String buyOrderReq = String.valueOf(new Random().nextInt(Integer.MAX_VALUE));
        String buyOrderMallOne = String.valueOf(new Random().nextInt(Integer.MAX_VALUE));
        String buyOrderMallTwo = String.valueOf(new Random().nextInt(Integer.MAX_VALUE));
        double amountMallOne = 1000d;
        double amountMallTwo = 1000d;
        byte installmentsNumberMallOne = 1;
        byte installmentsNumberMallTwo = 1;

        MallTransactionCreateDetails details = MallTransactionCreateDetails.build()
                .add(amountMallOne, COMMERCE_CODE_1, buyOrderMallOne, installmentsNumberMallOne)
                .add(amountMallTwo, COMMERCE_CODE_2, buyOrderMallTwo, installmentsNumberMallTwo);

        final OneclickMallTransactionAuthorizeResponse response = (new Oneclick.MallTransaction(OPTION)).authorize(USERNAME, tbkUserReq, buyOrderReq, details);

        assertEquals(response.getBuyOrder(), BUY_ORDER);
        assertEquals(response.getCardDetail().getCardNumber(), CARD_NUMBER);
        assertEquals(response.getAccountingDate(), ACCOUNTING_DATE);
        assertEquals(response.getTransactionDate(), TRANSACTION_DATE);
        //details1
        assertEquals(response.getDetails().get(0).getAmount(), AMOUNT_1);
        assertEquals(response.getDetails().get(0).getStatus(), STATUS_1);
        assertEquals(response.getDetails().get(0).getAuthorizationCode(), AUTHORIZATION_CODE_1);
        assertEquals(response.getDetails().get(0).getPaymentTypeCode(), PAYMENT_TYPE_CODE_1);
        assertEquals(response.getDetails().get(0).getResponseCode(), RESPONSE_CODE_1);
        assertEquals(response.getDetails().get(0).getInstallmentsNumber(), INSTALLMENTS_NUMBER_1);
        assertEquals(response.getDetails().get(0).getCommerceCode(), COMMERCE_CODE_1);
        assertEquals(response.getDetails().get(0).getBuyOrder(), BUY_ORDER_1);
        //details2
        assertEquals(response.getDetails().get(1).getAmount(), AMOUNT_2);
        assertEquals(response.getDetails().get(1).getStatus(), STATUS_2);
        assertEquals(response.getDetails().get(1).getAuthorizationCode(), AUTHORIZATION_CODE_2);
        assertEquals(response.getDetails().get(1).getPaymentTypeCode(), PAYMENT_TYPE_CODE_2);
        assertEquals(response.getDetails().get(1).getResponseCode(), RESPONSE_CODE_2);
        assertEquals(response.getDetails().get(1).getInstallmentsNumber(), INSTALLMENTS_NUMBER_2);
        assertEquals(response.getDetails().get(1).getCommerceCode(), COMMERCE_CODE_2);
        assertEquals(response.getDetails().get(1).getBuyOrder(), BUY_ORDER_2);
    }

    @Test
    public void refund() throws IOException, TransactionRefundException {
        String url = String.format("/%s/transactions/%s/refunds", API_URL, BUY_ORDER);

        String type = "REVERSED";

        Map<String, Object> mapResponse = new HashMap<String, Object>();
        mapResponse.put("type", type);

        Gson gson = new GsonBuilder().create();
        String jsonResponse = gson.toJson(mapResponse);
        setResponsePost(url, jsonResponse);

        String childCommerceCode = "597055555542";
        String childBuyOrder = "2019439134";
        double amount = 1000d;
        final OneclickMallTransactionRefundResponse response = (new Oneclick.MallTransaction(OPTION)).refund(BUY_ORDER, childCommerceCode, childBuyOrder, amount);

        assertEquals(response.getType(), type);
    }

    @Test
    public void status() throws IOException, TransactionStatusException {
        String url = String.format("/%s/transactions/%s", API_URL, BUY_ORDER);

        Map<String, Object> mapResponse = generateAutorizeJsonResponse();
        Gson gson = new GsonBuilder().create();
        setResponseGet(url, gson.toJson(mapResponse));

        final OneclickMallTransactionStatusResponse response = (new Oneclick.MallTransaction(OPTION)).status(BUY_ORDER);

        assertEquals(response.getBuyOrder(), BUY_ORDER);
        assertEquals(response.getCardDetail().getCardNumber(), CARD_NUMBER);
        assertEquals(response.getAccountingDate(), ACCOUNTING_DATE);
        assertEquals(response.getTransactionDate(), TRANSACTION_DATE);
        //details1
        assertEquals(response.getDetails().get(0).getAmount(), AMOUNT_1);
        assertEquals(response.getDetails().get(0).getStatus(), STATUS_1);
        assertEquals(response.getDetails().get(0).getAuthorizationCode(), AUTHORIZATION_CODE_1);
        assertEquals(response.getDetails().get(0).getPaymentTypeCode(), PAYMENT_TYPE_CODE_1);
        assertEquals(response.getDetails().get(0).getResponseCode(), RESPONSE_CODE_1);
        assertEquals(response.getDetails().get(0).getInstallmentsNumber(), INSTALLMENTS_NUMBER_1);
        assertEquals(response.getDetails().get(0).getCommerceCode(), COMMERCE_CODE_1);
        assertEquals(response.getDetails().get(0).getBuyOrder(), BUY_ORDER_1);
        //details2
        assertEquals(response.getDetails().get(1).getAmount(), AMOUNT_2);
        assertEquals(response.getDetails().get(1).getStatus(), STATUS_2);
        assertEquals(response.getDetails().get(1).getAuthorizationCode(), AUTHORIZATION_CODE_2);
        assertEquals(response.getDetails().get(1).getPaymentTypeCode(), PAYMENT_TYPE_CODE_2);
        assertEquals(response.getDetails().get(1).getResponseCode(), RESPONSE_CODE_2);
        assertEquals(response.getDetails().get(1).getInstallmentsNumber(), INSTALLMENTS_NUMBER_2);
        assertEquals(response.getDetails().get(1).getCommerceCode(), COMMERCE_CODE_2);
        assertEquals(response.getDetails().get(1).getBuyOrder(), BUY_ORDER_2);
    }

    @Test
    public void capture() throws IOException, TransactionCaptureException {
        String url = String.format("/%s/transactions/capture",API_URL);

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

        String childCommerceCode = "597055555542";
        String childBuyOrder = "2019439134";
        double amount = 1000d;
        final OneclickMallTransactionCaptureResponse response = (new Oneclick.MallTransaction(OPTION)).capture(childCommerceCode, childBuyOrder, authorizationCode, amount);
        assertEquals(response.getAuthorizationCode(), authorizationCode);
        assertEquals(response.getAuthorizationDate(), authorizationDate);
        assertEquals(response.getCapturedAmount(), capturedAmount);
        assertEquals(response.getResponseCode(), responseCode);
    }

    //{"username":"goncafa","tbk_user":"aaaaaaaaaaaaa-bbbbbbbb-cccccc"}
    /*
    @Test
    public void delete() throws IOException, InscriptionDeleteException {
        Oneclick.setIntegrationType(IntegrationType.SERVER_MOCK);
        String url = "/rswebpaytransaction/api/oneclick/v1.0/inscriptions";

        //String tbkUser = "aaaaaaaaaaaaa-bbbbbbbb-cccccc";
        Map<String, Object> mapResponse = new HashMap<>();
        Gson gson = new GsonBuilder().create();
        setResponse(url, gson.toJson(mapResponse));
        Oneclick.MallDeferredInscription.delete(username, tbkUser);

    }*/
}
