package webpayplus;

import cl.transbank.common.ApiConstants;
import cl.transbank.common.IntegrationApiKeys;
import cl.transbank.common.IntegrationCommerceCodes;
import cl.transbank.common.IntegrationType;
import cl.transbank.common.IntegrationTypeHelper;
import cl.transbank.model.Options;
import cl.transbank.patpass.PatpassComercio;
import cl.transbank.patpass.model.PatpassOptions;
import cl.transbank.patpass.responses.PatpassComercioInscriptionStartResponse;
import cl.transbank.patpass.responses.PatpassComercioTransactionStatusResponse;
import cl.transbank.webpay.exception.InscriptionStartException;
import cl.transbank.webpay.exception.TransactionStatusException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockserver.model.HttpRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;

class PatpassComercioTest extends TestBase {

    private static final String BUILDER_COMMERCE_CODE = "597012345678";
    private static final String BUILDER_API_KEY = "patpass_builder_api_key";
    private static final String PATPASS_INTEGRATION_URL = "https://pagoautomaticocontarjetasint.transbank.cl";
    private static final String PATPASS_PRODUCTION_URL = "https://www.pagoautomaticocontarjetas.cl";
    private static String apiUrl = ApiConstants.PATPASS_COMERCIO_ENDPOINT;
    private static Options option = new PatpassOptions(IntegrationCommerceCodes.PATPASS_COMERCIO,
            IntegrationApiKeys.PATPASS_COMERCIO, IntegrationType.SERVER_MOCK);
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
    void buildForIntegrationSetsExplicitCredentialValues() {
        PatpassComercio.Inscription inscription = PatpassComercio.Inscription.buildForIntegration(
                BUILDER_COMMERCE_CODE,
                BUILDER_API_KEY);

        assertNotNull(inscription);

        Options options = inscription.getOptions();
        assertTrue(options instanceof PatpassOptions);

        PatpassOptions patpassOptions = (PatpassOptions) options;
        assertEquals(BUILDER_COMMERCE_CODE, patpassOptions.getCommerceCode());
        assertEquals(BUILDER_API_KEY, patpassOptions.getApiKey());
        assertEquals(IntegrationType.TEST, patpassOptions.getIntegrationType());
        assertEquals(PATPASS_INTEGRATION_URL,
                IntegrationTypeHelper.getPatpassIntegrationType(patpassOptions.getIntegrationType()));
    }

    @Test
    void buildForProductionSetsExplicitCredentialValues() {
        PatpassComercio.Inscription inscription = PatpassComercio.Inscription.buildForProduction(
                BUILDER_COMMERCE_CODE,
                BUILDER_API_KEY);

        assertNotNull(inscription);

        Options options = inscription.getOptions();
        assertTrue(options instanceof PatpassOptions);

        PatpassOptions patpassOptions = (PatpassOptions) options;
        assertEquals(BUILDER_COMMERCE_CODE, patpassOptions.getCommerceCode());
        assertEquals(BUILDER_API_KEY, patpassOptions.getApiKey());
        assertEquals(IntegrationType.LIVE, patpassOptions.getIntegrationType());
        assertEquals(PATPASS_PRODUCTION_URL,
                IntegrationTypeHelper.getPatpassIntegrationType(patpassOptions.getIntegrationType()));
    }

    @Test
    void start() throws IOException, InscriptionStartException {
        
        String url = String.format("/%s/patInscription", apiUrl);

        String urlResponse = "https://pagoautomaticocontarjetasint.transbank.cl/nuevo-ic-rest/tokenComercioLogin";
        Map<String, Object> mapResponse = new HashMap<String, Object>();
        mapResponse.put(ApiConstants.TOKEN_TEXT, testToken);
        mapResponse.put("url", urlResponse);

        Gson gson = new GsonBuilder().create();
        String jsonResponse = gson.toJson(mapResponse);
        setResponsePost(url, jsonResponse);

        String urlRequest = "http://localhost:8081/patpass-comercio/commit";
        String name = "nombre";
        String firstLastName = "apellido";
        String secondLastName = "sapellido";
        String rut = "14140066-5";
        String serviceId = String.valueOf(new Random().nextInt(Integer.MAX_VALUE));
        String finalUrl = "http://localhost:8081/patpass-comercio/final";
        double maxAmount = 0;
        String phoneNumber = "123456734";
        String mobileNumber = "123456723";
        String patpassName = "nombre del patpass";
        String personEmail = "alba.cardenas@continuum.cl";
        String commerceEmail = "alba.cardenas@continuum.cl";
        String address = "huerfanos 101";
        String city = "Santiago";

        final PatpassComercioInscriptionStartResponse response = (new PatpassComercio.Inscription(option)).start(urlRequest,
                name,
                firstLastName,
                secondLastName,
                rut,
                serviceId,
                finalUrl,
                maxAmount,
                phoneNumber,
                mobileNumber,
                patpassName,
                personEmail,
                commerceEmail,
                address,
                city);

        assertEquals(response.getToken(), testToken);
        assertEquals(response.getUrl(), urlResponse);
    }

    @Test
    void status() throws IOException, TransactionStatusException {
        
        String url = String.format("/%s/status", apiUrl);

        String urlResponse = "https://pagoautomaticocontarjetasint.transbank.cl/nuevo-ic-rest/tokenVoucherLogin";
        Map<String, Object> mapResponse = new HashMap<String, Object>();
        mapResponse.put("authorized", (Boolean)true);
        mapResponse.put("voucherUrl", urlResponse);

        Gson gson = new GsonBuilder().create();
        String jsonResponse = gson.toJson(mapResponse);
        setResponsePost(url, jsonResponse);

        final PatpassComercioTransactionStatusResponse response = (new PatpassComercio.Inscription(option)).status(testToken);

        assertTrue(response.isAuthorized());
        assertEquals(response.getVoucherUrl(), urlResponse);
    }

    @Test
    void statusSendsPatpassHeadersWithExpectedNamesAndValues() throws IOException, TransactionStatusException {
        String url = String.format("/%s/status", apiUrl);

        Map<String, Object> mapResponse = new HashMap<String, Object>();
        mapResponse.put("authorized", (Boolean)true);
        mapResponse.put("voucherUrl", "https://example.com/voucher");

        Gson gson = new GsonBuilder().create();
        String jsonResponse = gson.toJson(mapResponse);
        setResponsePost(url, jsonResponse);

        (new PatpassComercio.Inscription(option)).status(testToken);

        client.verify(
                new HttpRequest()
                        .withMethod("POST")
                        .withPath(url)
                        .withHeader(ApiConstants.HEADER_PATPASS_COMMERCE_CODE_NAME, option.getCommerceCode())
                        .withHeader(ApiConstants.HEADER_PATPASS_API_KEY_NAME, option.getApiKey()));
    }
}
