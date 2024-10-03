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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockserver.model.HttpStatusCode;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;

public class OneclickMallTest  extends OneclickMallTestBase {

    private static String apiUrl = ApiConstants.ONECLICK_ENDPOINT;
    private static Options option = new WebpayOptions(IntegrationCommerceCodes.ONECLICK_MALL,
            IntegrationApiKeys.WEBPAY, IntegrationType.SERVER_MOCK);
    private static String username = "goncafa";
    private static String email = "gonzalo.castillo@continuum.cl";
    private static String tbkUser = "aaaaaaaaaaaaa-bbbbbbbb-cccccc";
    private static String commerceCode1 = "597055555542";
    private static String commerceCode2 = "597055555543";
    private static String testToken = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";

    @BeforeAll
    static void startProxy() {
        client = startClientAndServer(8888);
    }

    @AfterAll
    static void stopProxy() {
        client.stop();
    }

    @AfterEach
    void resetMockServer() {
        client.reset();
    }

    @Test
    void start() throws IOException, InscriptionStartException {
        String url = String.format("/%s/inscriptions",apiUrl);

        String urlResponse = "https://webpay3gint.transbank.cl/webpayserver/bp_multicode_inscription.cgi";
        Map<String, Object> mapResponse = new HashMap<String, Object>();
        mapResponse.put(ApiConstants.TOKEN_TEXT, testToken);
        mapResponse.put("url_webpay", urlResponse);

        Gson gson = new GsonBuilder().create();
        String jsonResponse = gson.toJson(mapResponse);
        setResponsePost(url, jsonResponse);

        String returnUrl = "http://localhost:8081/oneclick-mall/finish";

        final OneclickMallInscriptionStartResponse response = (new Oneclick.MallInscription(option)).start(username, email, returnUrl);
        assertEquals(response.getToken(), testToken);
        assertEquals(response.getUrlWebpay(), urlResponse);
    }

    @Test
    void finish() throws IOException, InscriptionFinishException {
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

        final OneclickMallInscriptionFinishResponse response = (new Oneclick.MallInscription(option)).finish(testToken);
        assertEquals(response.getResponseCode(), responseCode);
        assertEquals(response.getTbkUser(), tbkUser);
        assertEquals(response.getAuthorizationCode(), authorizationCode);
        assertEquals(response.getCardType(), cardType);
        assertEquals(response.getCardNumber(), cardNumber);

    }

    @Test
    void delete() throws IOException, InscriptionDeleteException {
        String url = String.format("/%s/inscriptions", apiUrl);
        setResponseDelete(url);
        final boolean response = (new Oneclick.MallInscription(option)).delete(tbkUser, username);
        assertTrue(response);
    }

    @Test
    void deleteNotFound() throws IOException, InscriptionDeleteException {
        String url = String.format("/%s/inscriptions", apiUrl);
        setResponseDeleteError(url, HttpStatusCode.NOT_FOUND_404);
        final boolean response = (new Oneclick.MallInscription(option)).delete(tbkUser, username);
        assertFalse(response);
    }
    @Test
    void authorize() throws IOException, TransactionAuthorizeException {
        OneclickMallTransactionStatusResponse expectedResponse = generateStatusResponse();
        String url = String.format("/%s/transactions",apiUrl);
        setResponsePost(url, generateJsonResponse());

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

        final OneclickMallTransactionAuthorizeResponse response = (new Oneclick.MallTransaction(option)).authorize(username, tbkUserReq, buyOrderReq, details);

        assertEquals(response.getBuyOrder(), expectedResponse.getBuyOrder());
        assertEquals(response.getCardDetail().getCardNumber(), expectedResponse.getCardDetail().getCardNumber());
        assertEquals(response.getAccountingDate(), expectedResponse.getAccountingDate());
        assertEquals(response.getTransactionDate(), expectedResponse.getTransactionDate());
        //details1
        OneclickMallTransactionStatusResponse.Detail expectedDetail1 = expectedResponse.getDetails().get(0);
        OneclickMallTransactionAuthorizeResponse.Detail detail1 = response.getDetails().get(0);
        assertEquals(detail1.getAmount(), expectedDetail1.getAmount());
        assertEquals(detail1.getStatus(), expectedDetail1.getStatus());
        assertEquals(detail1.getAuthorizationCode(), expectedDetail1.getAuthorizationCode());
        assertEquals(detail1.getPaymentTypeCode(), expectedDetail1.getPaymentTypeCode());
        assertEquals(detail1.getResponseCode(), expectedDetail1.getResponseCode());
        assertEquals(detail1.getInstallmentsNumber(), expectedDetail1.getInstallmentsNumber());
        assertEquals(detail1.getCommerceCode(), expectedDetail1.getCommerceCode());
        assertEquals(detail1.getBuyOrder(), expectedDetail1.getBuyOrder());
        //details2
        OneclickMallTransactionStatusResponse.Detail expectedDetail2 = expectedResponse.getDetails().get(1);
        OneclickMallTransactionAuthorizeResponse.Detail detail2 = response.getDetails().get(1);
        assertEquals(detail2.getAmount(), expectedDetail2.getAmount());
        assertEquals(detail2.getStatus(), expectedDetail2.getStatus());
        assertEquals(detail2.getAuthorizationCode(), expectedDetail2.getAuthorizationCode());
        assertEquals(detail2.getPaymentTypeCode(), expectedDetail2.getPaymentTypeCode());
        assertEquals(detail2.getResponseCode(), expectedDetail2.getResponseCode());
        assertEquals(detail2.getInstallmentsNumber(), expectedDetail2.getInstallmentsNumber());
        assertEquals(detail2.getCommerceCode(), expectedDetail2.getCommerceCode());
        assertEquals(detail2.getBuyOrder(), expectedDetail2.getBuyOrder());
    }

    @Test
    void refund() throws IOException, TransactionRefundException {
        String buyOrder = "1643997337";
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
        final OneclickMallTransactionRefundResponse response = (new Oneclick.MallTransaction(option)).refund(buyOrder, childCommerceCode, childBuyOrder, amount);

        assertEquals(response.getType(), type);
    }

    @Test
    void status() throws IOException, TransactionStatusException {
        OneclickMallTransactionStatusResponse expectedResponse = generateStatusResponse();
        String url = String.format("/%s/transactions/%s", apiUrl, expectedResponse.getBuyOrder());
        setResponseGet(url, generateJsonResponse());

        final OneclickMallTransactionStatusResponse response = (new Oneclick.MallTransaction(option)).status(expectedResponse.getBuyOrder());

        assertEquals(response.getBuyOrder(), expectedResponse.getBuyOrder());
        assertEquals(response.getCardDetail().getCardNumber(), expectedResponse.getCardDetail().getCardNumber());
        assertEquals(response.getAccountingDate(), expectedResponse.getAccountingDate());
        assertEquals(response.getTransactionDate(), expectedResponse.getTransactionDate());
        //details1
        OneclickMallTransactionStatusResponse.Detail expectedDetail1 = expectedResponse.getDetails().get(0);
        OneclickMallTransactionStatusResponse.Detail detail1 = response.getDetails().get(0);
        assertEquals(detail1.getAmount(), expectedDetail1.getAmount());
        assertEquals(detail1.getStatus(), expectedDetail1.getStatus());
        assertEquals(detail1.getAuthorizationCode(), expectedDetail1.getAuthorizationCode());
        assertEquals(detail1.getPaymentTypeCode(), expectedDetail1.getPaymentTypeCode());
        assertEquals(detail1.getResponseCode(), expectedDetail1.getResponseCode());
        assertEquals(detail1.getInstallmentsNumber(), expectedDetail1.getInstallmentsNumber());
        assertEquals(detail1.getCommerceCode(), expectedDetail1.getCommerceCode());
        assertEquals(detail1.getBuyOrder(), expectedDetail1.getBuyOrder());
        //details2
        OneclickMallTransactionStatusResponse.Detail expectedDetail2 = expectedResponse.getDetails().get(1);
        OneclickMallTransactionStatusResponse.Detail detail2 = response.getDetails().get(1);
        assertEquals(detail2.getAmount(), expectedDetail2.getAmount());
        assertEquals(detail2.getStatus(), expectedDetail2.getStatus());
        assertEquals(detail2.getAuthorizationCode(), expectedDetail2.getAuthorizationCode());
        assertEquals(detail2.getPaymentTypeCode(), expectedDetail2.getPaymentTypeCode());
        assertEquals(detail2.getResponseCode(), expectedDetail2.getResponseCode());
        assertEquals(detail2.getInstallmentsNumber(), expectedDetail2.getInstallmentsNumber());
        assertEquals(detail2.getCommerceCode(), expectedDetail2.getCommerceCode());
        assertEquals(detail2.getBuyOrder(), expectedDetail2.getBuyOrder());
    }



}
