package webpayplus;

import cl.transbank.common.IntegrationType;
import cl.transbank.model.MallTransactionCreateDetails;
import cl.transbank.webpay.exception.*;
import cl.transbank.webpay.webpayplus.WebpayPlus;
import cl.transbank.webpay.webpayplus.model.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Test;
import org.mockserver.configuration.ConfigurationProperties;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.junit.jupiter.MockServerSettings;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.mockserver.model.HttpStatusCode;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MockServerSettings(ports = {8787, 8888})
public class WebPayPlusMallDeferredTest {

    private final ClientAndServer client;
    public WebPayPlusMallDeferredTest(ClientAndServer client) {
        this.client = client;
    }

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


    private void setResponse(String url, String jsonResponse){
        client.when(new HttpRequest().withMethod("POST").withPath(url))
                .respond(new HttpResponse().withStatusCode(HttpStatusCode.ACCEPTED_202.code())
                        .withBody(jsonResponse));
        client.when(new HttpRequest().withMethod("GET").withPath(url))
                .respond(new HttpResponse().withStatusCode(HttpStatusCode.ACCEPTED_202.code())
                        .withBody(jsonResponse));
        client.when(new HttpRequest().withMethod("PUT").withPath(url))
                .respond(new HttpResponse().withStatusCode(HttpStatusCode.OK_200.code())
                        .withBody(jsonResponse));
    }

    @Test
    public void create() throws IOException, TransactionCreateException {
        WebpayPlus.MallDeferredTransaction.setIntegrationType(IntegrationType.SERVER_MOCK);
        String token = "01ab33cb02f389be7e912ca33d459fab7ee76e8d34116a75d946076fc1ec1cd2";
        String url = "/rswebpaytransaction/api/webpay/v1.0/transactions";

        String urlResponse = "https://webpay3gint.transbank.cl/webpayserver/initTransaction";
        Map<String, Object> mapResponse = new HashMap<String, Object>();
        mapResponse.put("token", token);
        mapResponse.put("url", urlResponse);

        Gson gson = new GsonBuilder().create();
        String jsonResponse = gson.toJson(mapResponse);
        setResponse(url, jsonResponse);
        String returnUrl = "http://wwww.google.com";

        String buyOrder = String.valueOf(new Random().nextInt(Integer.MAX_VALUE));
        String sessionId = String.valueOf(new Random().nextInt(Integer.MAX_VALUE));

        String buyOrderMallOne = String.valueOf(new Random().nextInt(Integer.MAX_VALUE));
        double amountMallOne = 1000;
        String mallOneCommerceCode = "597055555536";

        String buyOrderMallTwo = String.valueOf(new Random().nextInt(Integer.MAX_VALUE));
        double amountMallTwo = 1000;
        String mallTwoCommerceCode = "597055555537";

        final MallTransactionCreateDetails mallDetails = MallTransactionCreateDetails.build()
                .add(amountMallOne, mallOneCommerceCode, buyOrderMallOne)
                .add(amountMallTwo, mallTwoCommerceCode, buyOrderMallTwo);

        final WebpayPlusMallTransactionCreateResponse response = WebpayPlus.MallDeferredTransaction.create(buyOrder,sessionId, returnUrl, mallDetails);
        assertEquals(response.getToken(), token);
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
        WebpayPlus.MallDeferredTransaction.setIntegrationType(IntegrationType.SERVER_MOCK);
        String token = "01abfa9e931185df8171bb1f98c1ceb65d67ca699e47b900ab8eb13851ca99fb";
        String url = String.format("/rswebpaytransaction/api/webpay/v1.0/transactions/%s",token);

        Map<String, Object> mapResponse = generateCommitJsonResponse();
        Gson gson = new GsonBuilder().create();
        setResponse(url, gson.toJson(mapResponse));

        final WebpayPlusMallTransactionCommitResponse response = WebpayPlus.MallDeferredTransaction.commit(token);

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
        WebpayPlus.MallDeferredTransaction.setIntegrationType(IntegrationType.SERVER_MOCK);
        String token = "01abfa9e931185df8171bb1f98c1ceb65d67ca699e47b900ab8eb13851ca99fb";
        String url = String.format("/rswebpaytransaction/api/webpay/v1.0/transactions/%s/refunds",token);

        String type = "REVERSED";

        Map<String, Object> mapResponse = new HashMap<String, Object>();
        mapResponse.put("type", type);

        Gson gson = new GsonBuilder().create();
        String jsonResponse = gson.toJson(mapResponse);
        setResponse(url, jsonResponse);

        String childBuyOrder = "500894028";
        String childCommerceCode = "597055555536";
        double amount = 1000d;

        final WebpayPlusMallTransactionRefundResponse response = WebpayPlus.MallDeferredTransaction.refund(token, childBuyOrder, childCommerceCode, amount);
        assertEquals(response.getType(), type);

    }

    @Test
    public void status() throws IOException, TransactionStatusException {
        WebpayPlus.MallDeferredTransaction.setIntegrationType(IntegrationType.SERVER_MOCK);
        String token = "01abfa9e931185df8171bb1f98c1ceb65d67ca699e47b900ab8eb13851ca99fb";
        String url = String.format("/rswebpaytransaction/api/webpay/v1.0/transactions/%s",token);

        Map<String, Object> mapResponse = generateCommitJsonResponse();
        Gson gson = new GsonBuilder().create();
        setResponse(url, gson.toJson(mapResponse));

        final WebpayPlusMallTransactionStatusResponse response = WebpayPlus.MallDeferredTransaction.status(token);

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
        WebpayPlus.MallDeferredTransaction.setIntegrationType(IntegrationType.SERVER_MOCK);
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
        setResponse(url, jsonResponse);

        final WebpayPlusMallTransactionCaptureResponse response = WebpayPlus.MallDeferredTransaction.capture(token, commerceCode1, buyOrder1, authorizationCode1, amount1);
        assertEquals(response.getAuthorizationCode(), authorizationCode);
        assertEquals(response.getAuthorizationDate(), authorizationDate);
        assertEquals(response.getCapturedAmount(), capturedAmount);
        assertEquals(response.getResponseCode(), responseCode);
    }
}
