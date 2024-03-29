package webpayplus;

import cl.transbank.common.ApiConstants;
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

    private static String apiUrl = ApiConstants.ONECLICK_ENDPOINT;

    private static String username = "goncafa";
    private static String email = "gonzalo.castillo@continuum.cl";
    private static String tbkUser = "aaaaaaaaaaaaa-bbbbbbbb-cccccc";

    private static String cardNumber = "6623";
    private static String transactionDate = "2021-08-01T05:30:06.557Z";
    private static String accountingDate = "0801";
    private static String buyOrder = "724900565";

    private static double amount1 = 1000d;
    private static String status1 = "AUTHORIZED";
    private static String authorizationCode1 = "1213";
    private static String paymentTypeCode1 = "VN";
    private static byte installmentsNumber1 = 0;
    private static String commerceCode1 = "597055555542";
    private static String buyOrder1 = "2019439134";
    private static byte responseCode1 = 0;

    private static double amount2 = 1000d;
    private static String status2 = "AUTHORIZED";
    private static String authorizationCode2 = "1213";
    private static String paymentTypeCode2 = "VN";
    private static byte installmentsNumber2 = 0;
    private static String commerceCode2 = "597055555543";
    private static String buyOrder2 = "353345213";
    private static byte responseCode2 = 0;

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
    public void start() throws IOException, InscriptionStartException {
        Oneclick.configureForMock();
        String url = String.format("/%s/inscriptions",apiUrl);

        String urlResponse = "https://webpay3gint.transbank.cl/webpayserver/bp_multicode_inscription.cgi";
        Map<String, Object> mapResponse = new HashMap<String, Object>();
        mapResponse.put(ApiConstants.TOKEN_TEXT, testToken);
        mapResponse.put("url_webpay", urlResponse);

        Gson gson = new GsonBuilder().create();
        String jsonResponse = gson.toJson(mapResponse);
        setResponsePost(url, jsonResponse);

        String returnUrl = "http://localhost:8081/oneclick-mall/finish";

        final OneclickMallInscriptionStartResponse response = (new Oneclick.MallInscription()).start(username, email, returnUrl);
        assertEquals(response.getToken(), testToken);
        assertEquals(response.getUrlWebpay(), urlResponse);
    }


    @Test
    public void finish() throws IOException, InscriptionFinishException {
        Oneclick.configureForMock();
        String url = String.format("/%s/inscriptions/%s", apiUrl, testToken);

        byte responseCode = 0;
        String authorizationCode = "1213";
        String cardType = "Visa";
        String cardNumber = "XXXXXXXXXXXX6623";

        Map<String, Object> mapResponse = new HashMap<String, Object>();
        mapResponse.put("response_code", responseCode);
        mapResponse.put("tbk_user", tbkUser);
        mapResponse.put("authorization_code", authorizationCode);
        mapResponse.put("card_type", cardType);
        mapResponse.put("card_number", cardNumber);

        Gson gson = new GsonBuilder().create();
        String jsonResponse = gson.toJson(mapResponse);
        setResponsePut(url, jsonResponse);

        final OneclickMallInscriptionFinishResponse response = (new Oneclick.MallInscription()).finish(testToken);
        assertEquals(response.getResponseCode(), responseCode);
        assertEquals(response.getTbkUser(), tbkUser);
        assertEquals(response.getAuthorizationCode(), authorizationCode);
        assertEquals(response.getCardType(), cardType);
        assertEquals(response.getCardNumber(), cardNumber);

    }


     private Map<String, Object> generateAutorizeJsonResponse(){

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
        mapResponse.put("buy_order", buyOrder);
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
    public void authorize() throws IOException, TransactionAuthorizeException {
        Oneclick.configureForMock();
        String url = String.format("/%s/transactions",apiUrl);

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
                .add(amountMallOne, commerceCode1, buyOrderMallOne, installmentsNumberMallOne)
                .add(amountMallTwo, commerceCode2, buyOrderMallTwo, installmentsNumberMallTwo);

        final OneclickMallTransactionAuthorizeResponse response = (new Oneclick.MallTransaction()).authorize(username, tbkUserReq, buyOrderReq, details);

        assertEquals(response.getBuyOrder(), buyOrder);
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
        Oneclick.configureForMock();
        String url = String.format("/%s/transactions/%s/refunds", apiUrl, buyOrder);

        String type = "REVERSED";

        Map<String, Object> mapResponse = new HashMap<String, Object>();
        mapResponse.put("type", type);

        Gson gson = new GsonBuilder().create();
        String jsonResponse = gson.toJson(mapResponse);
        setResponsePost(url, jsonResponse);

        String childCommerceCode = "597055555542";
        String childBuyOrder = "2019439134";
        double amount = 1000d;
        final OneclickMallTransactionRefundResponse response = (new Oneclick.MallTransaction()).refund(buyOrder, childCommerceCode, childBuyOrder, amount);

        assertEquals(response.getType(), type);
    }

    @Test
    public void status() throws IOException, TransactionStatusException {
        Oneclick.configureForMock();
        String url = String.format("/%s/transactions/%s", apiUrl, buyOrder);

        Map<String, Object> mapResponse = generateAutorizeJsonResponse();
        Gson gson = new GsonBuilder().create();
        setResponseGet(url, gson.toJson(mapResponse));

        final OneclickMallTransactionStatusResponse response = (new Oneclick.MallTransaction()).status(buyOrder);

        assertEquals(response.getBuyOrder(), buyOrder);
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
        Oneclick.configureForMock();
        String url = String.format("/%s/transactions/capture",apiUrl);

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
        final OneclickMallTransactionCaptureResponse response = (new Oneclick.MallTransaction()).capture(childCommerceCode, childBuyOrder, authorizationCode, amount);
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
