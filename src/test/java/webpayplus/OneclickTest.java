package webpayplus;

import cl.transbank.common.IntegrationType;
import cl.transbank.model.Options;
import cl.transbank.webpay.common.WebpayOptions;
import cl.transbank.webpay.oneclick.Oneclick;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OneclickTest {

    private static final String COMMERCE_CODE = "123456789";
    private static final String API_KEY = "test_api_key";

    @Test
    public void testMallInscriptionBuildForIntegration() {
        Oneclick.MallInscription inscription = Oneclick.MallInscription.buildForIntegration(COMMERCE_CODE, API_KEY);

        assertNotNull(inscription);

        Options options = inscription.getOptions();
        assertTrue(options instanceof WebpayOptions);
        WebpayOptions webpayOptions = (WebpayOptions) options;

        assertEquals(COMMERCE_CODE, webpayOptions.getCommerceCode());
        assertEquals(API_KEY, webpayOptions.getApiKey());
        assertEquals(IntegrationType.TEST, webpayOptions.getIntegrationType());
    }

    @Test
    public void testMallInscriptionBuildForProduction() {
        Oneclick.MallInscription inscription = Oneclick.MallInscription.buildForProduction(COMMERCE_CODE, API_KEY);

        assertNotNull(inscription);
        assertEquals(IntegrationType.LIVE, ((WebpayOptions) inscription.getOptions()).getIntegrationType());
    }

    @Test
    public void testMallTransactionBuildForIntegration() {
        Oneclick.MallTransaction transaction = Oneclick.MallTransaction.buildForIntegration(COMMERCE_CODE, API_KEY);

        assertNotNull(transaction);
        assertEquals(IntegrationType.TEST, ((WebpayOptions) transaction.getOptions()).getIntegrationType());
    }

    @Test
    public void testMallTransactionBuildForProduction() {
        Oneclick.MallTransaction transaction = Oneclick.MallTransaction.buildForProduction(COMMERCE_CODE, API_KEY);

        assertNotNull(transaction);
        assertEquals(IntegrationType.LIVE, ((WebpayOptions) transaction.getOptions()).getIntegrationType());
    }

    @Test
    public void testMallBinInfoBuildForIntegration() {
        Oneclick.MallBinInfo binInfo = Oneclick.MallBinInfo.buildForIntegration(COMMERCE_CODE, API_KEY);

        assertNotNull(binInfo);
        assertEquals(IntegrationType.TEST, ((WebpayOptions) binInfo.getOptions()).getIntegrationType());
    }

    @Test
    public void testMallBinInfoBuildForProduction() {
        Oneclick.MallBinInfo binInfo = Oneclick.MallBinInfo.buildForProduction(COMMERCE_CODE, API_KEY);

        assertNotNull(binInfo);
        assertEquals(IntegrationType.LIVE, ((WebpayOptions) binInfo.getOptions()).getIntegrationType());
    }
}
