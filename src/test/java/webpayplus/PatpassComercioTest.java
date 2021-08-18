package webpayplus;

import cl.transbank.common.IntegrationType;
import cl.transbank.patpass.PatpassComercio;
import cl.transbank.patpass.model.PatpassComercioInscriptionStartResponse;
import cl.transbank.patpass.model.PatpassComercioTransactionStatusResponse;
import cl.transbank.webpay.exception.InscriptionStartException;
import cl.transbank.webpay.exception.TransactionStatusException;
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

public class PatpassComercioTest extends TestBase {

    private static String apiUrl = "/restpatpass/v1";
    private static String testToken = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";

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
        PatpassComercio.setIntegrationType(IntegrationType.SERVER_MOCK);
        String url = String.format("%s/services/patInscription", apiUrl);

        String urlResponse = "https://pagoautomaticocontarjetasint.transbank.cl/nuevo-ic-rest/tokenComercioLogin";
        Map<String, Object> mapResponse = new HashMap<String, Object>();
        mapResponse.put("token", testToken);
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
        String commerceCode = "28299257";
        double maxAmount = 0;
        String phoneNumber = "123456734";
        String mobileNumber = "123456723";
        String patpassName = "nombre del patpass";
        String personEmail = "alba.cardenas@continuum.cl";
        String commerceEmail = "alba.cardenas@continuum.cl";
        String address = "huerfanos 101";
        String city = "Santiago";

        PatpassComercio.setCommerceCode(commerceCode);
        final PatpassComercioInscriptionStartResponse response = PatpassComercio.Inscription.start(urlRequest,
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
    public void status() throws IOException, TransactionStatusException {
        PatpassComercio.setIntegrationType(IntegrationType.SERVER_MOCK);
        String url = String.format("%s/services/status", apiUrl);

        String urlResponse = "https://pagoautomaticocontarjetasint.transbank.cl/nuevo-ic-rest/tokenVoucherLogin";
        Map<String, Object> mapResponse = new HashMap<String, Object>();
        mapResponse.put("authorized", true);
        mapResponse.put("voucherUrl", urlResponse);

        Gson gson = new GsonBuilder().create();
        String jsonResponse = gson.toJson(mapResponse);
        setResponsePost(url, jsonResponse);

        String commerceCode = "28299257";
        PatpassComercio.setCommerceCode(commerceCode);
        final PatpassComercioTransactionStatusResponse response = PatpassComercio.Transaction.status(testToken);

        assertEquals(response.isAuthorized(), true);
        assertEquals(response.getVoucherUrl(), urlResponse);
    }
}
