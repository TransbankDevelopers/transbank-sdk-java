package cl.transbank.common;

import java.net.MalformedURLException;
import java.net.URL;

public class ApiUtil {
    public URL getWebpayEndpointUrl(IntegrationType integrationType) throws MalformedURLException {
        return new URL(String.format("%s/%s", IntegrationTypeHelper.getWebpayIntegrationType(integrationType), ApiConstants.WEBPAY_ENDPOINT));
    }
    public URL getOneclickEndpointUrl(IntegrationType integrationType) throws MalformedURLException {
        return new URL(String.format("%s/%s", IntegrationTypeHelper.getWebpayIntegrationType(integrationType), ApiConstants.ONECLICK_ENDPOINT));
    }
}
