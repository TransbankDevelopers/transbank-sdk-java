package webpayplus;

import cl.transbank.common.IntegrationType;
import cl.transbank.patpass.PatpassComercio;
import cl.transbank.patpass.model.PatpassComercioInscriptionStartResponse;
import cl.transbank.patpass.model.PatpassComercioTransactionStatusResponse;
import cl.transbank.webpay.exception.InscriptionStartException;
import cl.transbank.webpay.exception.TransactionStatusException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Test;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.junit.jupiter.MockServerSettings;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.mockserver.model.HttpStatusCode;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MockServerSettings(ports = {8787, 8888})
public class PatpassComercioTest {

    private final ClientAndServer client;
    public PatpassComercioTest(ClientAndServer client) {
        this.client = client;
    }

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
        client.when(new HttpRequest().withMethod("DELETE").withPath(url))
                .respond(new HttpResponse().withStatusCode(HttpStatusCode.OK_200.code())
                        .withBody(jsonResponse));
    }

    @Test
    public void start() throws IOException, InscriptionStartException {
        PatpassComercio.setIntegrationType(IntegrationType.SERVER_MOCK);
        String token = "7a0fb90db0119c8d5d976f38f80c21b45804ba8f3daa30678b2934b05f612a1d";
        String url = "/restpatpass/v1/services/patInscription";

        String urlResponse = "https://pagoautomaticocontarjetasint.transbank.cl/nuevo-ic-rest/tokenComercioLogin";
        Map<String, Object> mapResponse = new HashMap<String, Object>();
        mapResponse.put("token", token);
        mapResponse.put("url", urlResponse);

        Gson gson = new GsonBuilder().create();
        String jsonResponse = gson.toJson(mapResponse);
        setResponse(url, jsonResponse);

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

        assertEquals(response.getToken(), token);
        assertEquals(response.getUrl(), urlResponse);
    }

    @Test
    public void status() throws IOException, TransactionStatusException {
        PatpassComercio.setIntegrationType(IntegrationType.SERVER_MOCK);
        String token = "7a0fb90db0119c8d5d976f38f80c21b45804ba8f3daa30678b2934b05f612a1d";
        String url = "/restpatpass/v1/services/status";

        String urlResponse = "https://pagoautomaticocontarjetasint.transbank.cl/nuevo-ic-rest/tokenVoucherLogin";
        Map<String, Object> mapResponse = new HashMap<String, Object>();
        mapResponse.put("authorized", true);
        mapResponse.put("voucherUrl", urlResponse);

        Gson gson = new GsonBuilder().create();
        String jsonResponse = gson.toJson(mapResponse);
        setResponse(url, jsonResponse);

        String commerceCode = "28299257";
        PatpassComercio.setCommerceCode(commerceCode);
        final PatpassComercioTransactionStatusResponse response = PatpassComercio.Transaction.status(token);

        assertEquals(response.isAuthorized(), true);
        assertEquals(response.getVoucherUrl(), urlResponse);
    }
}
