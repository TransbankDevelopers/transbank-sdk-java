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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;

public class WebpayPlusMallDeferredTest extends WebpayPlusMallTestBase {

    private static String apiUrl = ApiConstants.WEBPAY_ENDPOINT;
    private static Options option = new WebpayOptions(IntegrationCommerceCodes.WEBPAY_PLUS_MALL_DEFERRED,
            IntegrationApiKeys.WEBPAY, IntegrationType.SERVER_MOCK);
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
    void create() throws IOException, TransactionCreateException {

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

        final WebpayPlusMallTransactionCreateResponse response = (new WebpayPlus.MallTransaction(option)).create(buyOrder3, sessionId3, returnUrl, mallDetails);
        assertEquals(response.getToken(), testToken);
        assertEquals(response.getUrl(), urlResponse);
    }

    @Test
    void commit() throws IOException, TransactionCommitException {
        WebpayPlusMallTransactionStatusResponse expectedResponse = generateStatusResponse();
        String url = String.format("/%s/transactions/%s", apiUrl, testToken);
        setResponsePut(url, generateCommitJsonResponse());

        final WebpayPlusMallTransactionCommitResponse response = (new WebpayPlus.MallTransaction(option)).commit(testToken);

        assertEquals(response.getVci(), expectedResponse.getVci());
        assertEquals(response.getBuyOrder(), expectedResponse.getBuyOrder());
        assertEquals(response.getSessionId(), expectedResponse.getSessionId());
        assertEquals(response.getCardDetail().getCardNumber(), expectedResponse.getCardDetail().getCardNumber());
        assertEquals(response.getAccountingDate(), expectedResponse.getAccountingDate());
        assertEquals(response.getTransactionDate(), expectedResponse.getTransactionDate());
        //details1
        WebpayPlusMallTransactionStatusResponse.Detail expectedDetail1 = expectedResponse.getDetails().get(0);
        WebpayPlusMallTransactionStatusResponse.Detail detail1 = response.getDetails().get(0);
        assertEquals(detail1.getAmount(), expectedDetail1.getAmount());
        assertEquals(detail1.getStatus(), expectedDetail1.getStatus());
        assertEquals(detail1.getAuthorizationCode(), expectedDetail1.getAuthorizationCode());
        assertEquals(detail1.getPaymentTypeCode(), expectedDetail1.getPaymentTypeCode());
        assertEquals(detail1.getResponseCode(), expectedDetail1.getResponseCode());
        assertEquals(detail1.getInstallmentsNumber(), expectedDetail1.getInstallmentsNumber());
        assertEquals(detail1.getCommerceCode(), expectedDetail1.getCommerceCode());
        assertEquals(detail1.getBuyOrder(), expectedDetail1.getBuyOrder());
        //details2
        WebpayPlusMallTransactionStatusResponse.Detail expectedDetail2 = expectedResponse.getDetails().get(1);
        WebpayPlusMallTransactionStatusResponse.Detail detail2 = response.getDetails().get(1);
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
    void status() throws IOException, TransactionStatusException {
        WebpayPlusMallTransactionStatusResponse expectedResponse = generateStatusResponse();
        String url = String.format("/%s/transactions/%s",apiUrl, testToken);
        setResponseGet(url, generateCommitJsonResponse());

        final WebpayPlusMallTransactionStatusResponse response = (new WebpayPlus.MallTransaction(option)).status(testToken);

        assertEquals(response.getVci(), expectedResponse.getVci());
        assertEquals(response.getBuyOrder(), expectedResponse.getBuyOrder());
        assertEquals(response.getSessionId(), expectedResponse.getSessionId());
        assertEquals(response.getCardDetail().getCardNumber(), expectedResponse.getCardDetail().getCardNumber());
        assertEquals(response.getAccountingDate(), expectedResponse.getAccountingDate());
        assertEquals(response.getTransactionDate(), expectedResponse.getTransactionDate());
        //details1
        WebpayPlusMallTransactionStatusResponse.Detail expectedDetail1 = expectedResponse.getDetails().get(0);
        WebpayPlusMallTransactionStatusResponse.Detail detail1 = response.getDetails().get(0);
        assertEquals(detail1.getAmount(), expectedDetail1.getAmount());
        assertEquals(detail1.getStatus(), expectedDetail1.getStatus());
        assertEquals(detail1.getAuthorizationCode(), expectedDetail1.getAuthorizationCode());
        assertEquals(detail1.getPaymentTypeCode(), expectedDetail1.getPaymentTypeCode());
        assertEquals(detail1.getResponseCode(), expectedDetail1.getResponseCode());
        assertEquals(detail1.getInstallmentsNumber(), expectedDetail1.getInstallmentsNumber());
        assertEquals(detail1.getCommerceCode(), expectedDetail1.getCommerceCode());
        assertEquals(detail1.getBuyOrder(), expectedDetail1.getBuyOrder());
        //details2
        WebpayPlusMallTransactionStatusResponse.Detail expectedDetail2 = expectedResponse.getDetails().get(1);
        WebpayPlusMallTransactionStatusResponse.Detail detail2 = response.getDetails().get(1);
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
    void capture() throws IOException, TransactionCaptureException {
        String url = String.format("/%s/transactions/%s/capture", apiUrl, testToken);

        String commerceCode = "597055555537";
        String buyOrder = "1936357040";
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

        final WebpayPlusMallTransactionCaptureResponse response = (new WebpayPlus.MallTransaction(option)).capture(commerceCode, testToken, buyOrder, authorizationCode, capturedAmount);
        assertEquals(response.getAuthorizationCode(), authorizationCode);
        assertEquals(response.getAuthorizationDate(), authorizationDate);
        assertEquals(response.getCapturedAmount(), capturedAmount);
        assertEquals(response.getResponseCode(), responseCode);
    }
}
