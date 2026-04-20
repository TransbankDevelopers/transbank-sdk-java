package webpayplus;

import cl.transbank.common.ApiConstants;
import cl.transbank.common.IntegrationApiKeys;
import cl.transbank.common.IntegrationCommerceCodes;
import cl.transbank.common.IntegrationType;
import cl.transbank.common.IntegrationTypeHelper;
import cl.transbank.model.Options;
import cl.transbank.webpay.common.WebpayOptions;
import cl.transbank.webpay.exception.TransactionCommitException;
import cl.transbank.webpay.exception.TransactionCreateException;
import cl.transbank.webpay.exception.TransactionRefundException;
import cl.transbank.webpay.exception.TransactionStatusException;
import cl.transbank.webpay.webpayplus.WebpayPlus;
import cl.transbank.webpay.webpayplus.responses.WebpayPlusTransactionCommitResponse;
import cl.transbank.webpay.webpayplus.responses.WebpayPlusTransactionCreateResponse;
import cl.transbank.webpay.webpayplus.responses.WebpayPlusTransactionRefundResponse;
import cl.transbank.webpay.webpayplus.responses.WebpayPlusTransactionStatusResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;

class WebpayPlusTest extends WebpayPlusTestBase {
    private static final String BUILDER_COMMERCE_CODE = "597012345678";
    private static final String BUILDER_API_KEY = "webpay_builder_api_key";
    private static final String WEBPAY_INTEGRATION_URL = "https://webpay3gint.transbank.cl";
    private static final String WEBPAY_PRODUCTION_URL = "https://webpay3g.transbank.cl";
    private static String apiUrl = ApiConstants.WEBPAY_ENDPOINT;
    private static Options option = new WebpayOptions(IntegrationCommerceCodes.WEBPAY_PLUS,
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
    void buildForIntegrationSetsExplicitCredentialValuesAndSharedWebpayUrl() {
        WebpayPlus.Transaction transaction = WebpayPlus.Transaction.buildForIntegration(
                BUILDER_COMMERCE_CODE,
                BUILDER_API_KEY);

        assertNotNull(transaction);

        Options options = transaction.getOptions();
        assertTrue(options instanceof WebpayOptions);

        WebpayOptions webpayOptions = (WebpayOptions) options;
        assertEquals(BUILDER_COMMERCE_CODE, webpayOptions.getCommerceCode());
        assertEquals(BUILDER_API_KEY, webpayOptions.getApiKey());
        assertEquals(IntegrationType.TEST, webpayOptions.getIntegrationType());
        assertEquals(WEBPAY_INTEGRATION_URL,
                IntegrationTypeHelper.getWebpayIntegrationType(webpayOptions.getIntegrationType()));
    }

    @Test
    void buildForProductionSetsExplicitCredentialValuesAndSharedWebpayUrl() {
        WebpayPlus.Transaction transaction = WebpayPlus.Transaction.buildForProduction(
                BUILDER_COMMERCE_CODE,
                BUILDER_API_KEY);

        assertNotNull(transaction);

        Options options = transaction.getOptions();
        assertTrue(options instanceof WebpayOptions);

        WebpayOptions webpayOptions = (WebpayOptions) options;
        assertEquals(BUILDER_COMMERCE_CODE, webpayOptions.getCommerceCode());
        assertEquals(BUILDER_API_KEY, webpayOptions.getApiKey());
        assertEquals(IntegrationType.LIVE, webpayOptions.getIntegrationType());
        assertEquals(WEBPAY_PRODUCTION_URL,
                IntegrationTypeHelper.getWebpayIntegrationType(webpayOptions.getIntegrationType()));
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

        String buyOrder = String.valueOf(new Random().nextInt(Integer.MAX_VALUE));
        String sessionId = String.valueOf(new Random().nextInt(Integer.MAX_VALUE));
        double amount = 1000;
        String returnUrl = "http://wwww.google.com";

        final WebpayPlusTransactionCreateResponse response = (new WebpayPlus.Transaction(option)).create(buyOrder, sessionId, amount, returnUrl);
        assertEquals(testToken, response.getToken());
        assertEquals(urlResponse, response.getUrl());
    }

    @Test
    void commit() throws IOException, TransactionCommitException {
        WebpayPlusTransactionStatusResponse expectedResponse = generateStatusResponse();
        String url = String.format("/%s/transactions/%s", apiUrl, testToken);
        setResponsePut(url, generateCommitJsonResponse());

        final WebpayPlusTransactionCommitResponse response = (new WebpayPlus.Transaction(option)).commit(testToken);
        assertEquals(expectedResponse.getVci(), response.getVci());
        assertEquals(expectedResponse.getAmount(), response.getAmount());
        assertEquals(expectedResponse.getStatus(), response.getStatus());
        assertEquals(expectedResponse.getBuyOrder(), response.getBuyOrder());
        assertEquals(expectedResponse.getSessionId(), response.getSessionId());
        assertEquals(expectedResponse.getCardDetail().getCardNumber(), response.getCardDetail().getCardNumber());
        assertEquals(expectedResponse.getAccountingDate(), response.getAccountingDate());
        assertEquals(expectedResponse.getTransactionDate(), response.getTransactionDate());
        assertEquals(expectedResponse.getAuthorizationCode(), response.getAuthorizationCode());
        assertEquals(expectedResponse.getPaymentTypeCode(), response.getPaymentTypeCode());
        assertEquals(expectedResponse.getResponseCode(), response.getResponseCode());
        assertEquals(expectedResponse.getInstallmentsNumber(), response.getInstallmentsNumber());
    }

    @Test
    void refund() throws IOException, TransactionRefundException {
        String url = String.format("/%s/transactions/%s/refunds", apiUrl, testToken);
        double amount = 1000d;
        String type = "REVERSED";

        Map<String, Object> mapResponse = new HashMap<String, Object>();
        mapResponse.put("type", type);

        Gson gson = new GsonBuilder().create();
        String jsonResponse = gson.toJson(mapResponse);
        setResponsePost(url, jsonResponse);

        final WebpayPlusTransactionRefundResponse response = (new WebpayPlus.Transaction(option)).refund(testToken, amount);
        assertEquals(type, response.getType());
    }

    @Test
    void status() throws IOException, TransactionStatusException {
        WebpayPlusTransactionStatusResponse expectedResponse = generateStatusResponse();
        String url = String.format("/%s/transactions/%s", apiUrl, testToken);
        setResponseGet(url, generateCommitJsonResponse());

        final WebpayPlusTransactionStatusResponse response = (new WebpayPlus.Transaction(option)).status(testToken);
        assertEquals(expectedResponse.getVci(), response.getVci());
        assertEquals(expectedResponse.getAmount(), response.getAmount());
        assertEquals(expectedResponse.getStatus(), response.getStatus());
        assertEquals(expectedResponse.getBuyOrder(), response.getBuyOrder());
        assertEquals(expectedResponse.getSessionId(), response.getSessionId());
        assertEquals(expectedResponse.getCardDetail().getCardNumber(), response.getCardDetail().getCardNumber());
        assertEquals(expectedResponse.getAccountingDate(), response.getAccountingDate());
        assertEquals(expectedResponse.getTransactionDate(), response.getTransactionDate());
        assertEquals(expectedResponse.getAuthorizationCode(), response.getAuthorizationCode());
        assertEquals(expectedResponse.getPaymentTypeCode(), response.getPaymentTypeCode());
        assertEquals(expectedResponse.getResponseCode(), response.getResponseCode());
        assertEquals(expectedResponse.getInstallmentsNumber(), response.getInstallmentsNumber());
    }

}
