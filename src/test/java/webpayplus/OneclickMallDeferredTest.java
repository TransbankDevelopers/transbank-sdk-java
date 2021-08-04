package webpayplus;

import cl.transbank.common.IntegrationType;
import cl.transbank.webpay.exception.*;
import cl.transbank.webpay.oneclick.Oneclick;
import cl.transbank.webpay.oneclick.model.*;
import cl.transbank.webpay.webpayplus.WebpayPlus;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockserver.client.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.junit.jupiter.MockServerExtension;
import org.mockserver.junit.jupiter.MockServerSettings;
import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockServerExtension.class)
@MockServerSettings(ports = {8888})
public class OneclickMallDeferredTest extends TestBase {


    public OneclickMallDeferredTest(MockServerClient client) {
        this.client = client;
    }

    private static String username = "goncafa";
    private static String email = "gonzalo.castillo@continuum.cl";
    private static String tbkUser = "12350bde-00dd-4ad8-9cc6-ae918022adc3";

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


    @Test
    public void start() throws IOException, InscriptionStartException {
        Oneclick.setIntegrationType(IntegrationType.SERVER_MOCK);
        String token = "01ab33cb02f389be7e912ca33d459fab7ee76e8d34116a75d946076fc1ec1cd2";
        String url = "/rswebpaytransaction/api/oneclick/v1.0/inscriptions";

        String urlResponse = "https://webpay3gint.transbank.cl/webpayserver/bp_multicode_inscription.cgi";
        Map<String, Object> mapResponse = new HashMap<String, Object>();
        mapResponse.put("token", token);
        mapResponse.put("url_webpay", urlResponse);

        Gson gson = new GsonBuilder().create();
        String jsonResponse = gson.toJson(mapResponse);
        setResponsePost(url, jsonResponse);

        String returnUrl = "http://localhost:8081/oneclick-mall/finish";

        final OneclickMallInscriptionStartResponse response = Oneclick.MallDeferredInscription.start(username, email, returnUrl);
        assertEquals(response.getToken(), token);
        assertEquals(response.getUrlWebpay(), urlResponse);
    }


    @Test
    public void finish() throws IOException, InscriptionFinishException {
        Oneclick.setIntegrationType(IntegrationType.SERVER_MOCK);
        String token = "01ab33cb02f389be7e912ca33d459fab7ee76e8d34116a75d946076fc1ec1cd2";
        String url = String.format("/rswebpaytransaction/api/oneclick/v1.0/inscriptions/%s", token);

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

        final OneclickMallInscriptionFinishResponse response = Oneclick.MallDeferredInscription.finish(token);
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
        Oneclick.setIntegrationType(IntegrationType.SERVER_MOCK);
        String token = "01ab33cb02f389be7e912ca33d459fab7ee76e8d34116a75d946076fc1ec1cd2";
        String url = "/rswebpaytransaction/api/oneclick/v1.0/transactions";

        Map<String, Object> mapResponse = generateAutorizeJsonResponse();
        Gson gson = new GsonBuilder().create();
        setResponsePost(url, gson.toJson(mapResponse));

        String tbkUserReq = "12350bde-00dd-4ad8-9cc6-ae918022adc3";
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

        final OneclickMallTransactionAuthorizeResponse response = Oneclick.MallDeferredTransaction.authorize(username, tbkUserReq, buyOrderReq, details);

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
        Oneclick.setIntegrationType(IntegrationType.SERVER_MOCK);
        String token = "01ab33cb02f389be7e912ca33d459fab7ee76e8d34116a75d946076fc1ec1cd2";
        String url = String.format("/rswebpaytransaction/api/oneclick/v1.0/transactions/%s/refunds", buyOrder);

        String type = "REVERSED";

        Map<String, Object> mapResponse = new HashMap<String, Object>();
        mapResponse.put("type", type);

        Gson gson = new GsonBuilder().create();
        String jsonResponse = gson.toJson(mapResponse);
        setResponsePost(url, jsonResponse);

        String childCommerceCode = "597055555542";
        String childBuyOrder = "2019439134";
        double amount = 1000d;
        final OneclickMallTransactionRefundResponse response = Oneclick.MallTransaction.refund(buyOrder, childCommerceCode, childBuyOrder, amount);

        assertEquals(response.getType(), type);
    }

    @Test
    public void status() throws IOException, TransactionStatusException {
        Oneclick.setIntegrationType(IntegrationType.SERVER_MOCK);
        String token = "01ab33cb02f389be7e912ca33d459fab7ee76e8d34116a75d946076fc1ec1cd2";
        String url = String.format("/rswebpaytransaction/api/oneclick/v1.0/transactions/%s", buyOrder);

        Map<String, Object> mapResponse = generateAutorizeJsonResponse();
        Gson gson = new GsonBuilder().create();
        setResponseGet(url, gson.toJson(mapResponse));

        final OneclickMallTransactionStatusResponse response = Oneclick.MallDeferredTransaction.status(buyOrder);

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
        WebpayPlus.MallDeferredTransaction.setIntegrationType(IntegrationType.SERVER_MOCK);
        String token = "01ab33cb02f389be7e912ca33d459fab7ee76e8d34116a75d946076fc1ec1cd2";
        String url = "/rswebpaytransaction/api/oneclick/v1.0/transactions/capture";

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
        final OneclickMallTransactionCaptureResponse response = Oneclick.MallDeferredTransaction.capture(childCommerceCode, childBuyOrder, authorizationCode, amount);
        assertEquals(response.getAuthorizationCode(), authorizationCode);
        assertEquals(response.getAuthorizationDate(), authorizationDate);
        assertEquals(response.getCapturedAmount(), capturedAmount);
        assertEquals(response.getResponseCode(), responseCode);
    }

    //{"username":"goncafa","tbk_user":"01ab5362d9b0eb7ebec6e822518eb6453cb4e6280ca2295370c703dd0fe71e3d"}
    /*
    @Test
    public void delete() throws IOException, InscriptionDeleteException {
        Oneclick.setIntegrationType(IntegrationType.SERVER_MOCK);
        String token = "01ab33cb02f389be7e912ca33d459fab7ee76e8d34116a75d946076fc1ec1cd2";
        String url = "/rswebpaytransaction/api/oneclick/v1.0/inscriptions";

        //String tbkUser = "12350bde-00dd-4ad8-9cc6-ae918022adc3";
        Map<String, Object> mapResponse = new HashMap<>();
        Gson gson = new GsonBuilder().create();
        setResponse(url, gson.toJson(mapResponse));
        Oneclick.MallDeferredInscription.delete(username, tbkUser);

    }*/
}
