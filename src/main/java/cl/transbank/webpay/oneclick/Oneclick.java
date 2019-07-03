package cl.transbank.webpay.oneclick;

import cl.transbank.util.HttpUtil;
import cl.transbank.webpay.IntegrationType;
import cl.transbank.webpay.Options;
import cl.transbank.webpay.Webpay;
import cl.transbank.webpay.WebpayApiResource;
import cl.transbank.webpay.exception.InscriptionStartException;
import cl.transbank.webpay.oneclick.model.StartOneclickInscriptionResponse;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.net.URL;

public class Oneclick {
    @Setter(AccessLevel.PRIVATE) @Getter(AccessLevel.PRIVATE) private static Options options = new Options();

    public static String getCurrentIntegrationTypeUrl(IntegrationType integrationType, String resource){
        if (null == integrationType)
            integrationType = IntegrationType.TEST;

        return String.format("%s/rswebpaytransaction/api/oneclick/v1.0/%s", integrationType.getApiBase(), resource);
    }

    public static void setCommerceCode(String commerceCode) {
        Oneclick.getOptions().setCommerceCode(commerceCode);
    }

    public static String getCommerceCode() {
        return Oneclick.getOptions().getCommerceCode();
    }

    public static void setApiKey(String apiKey) {
        Oneclick.getOptions().setApiKey(apiKey);
    }

    public static String getApiKey() {
        return Oneclick.getOptions().getApiKey();
    }

    public static void setIntegrationType(IntegrationType integrationType) {
        Oneclick.getOptions().setIntegrationType(integrationType);
    }

    public static IntegrationType getIntegrationType() {
        return Oneclick.getOptions().getIntegrationType();
    }

    public static Options buildOptionsForTestingOneclickMall(){
        return new Options ("",
                "579B532A7440BB0C9079DED94D31EA1615BACEB56610332264630D42D0A36B1C", IntegrationType.TEST);
    }

    public static void configureMallForTesting() {
        // TODO we have not the commerce code yet
        Oneclick.setOptions(new Options("",
                "579B532A7440BB0C9079DED94D31EA1615BACEB56610332264630D42D0A36B1C", IntegrationType.TEST));
    }

    public static void configureDeferredForTesting() {
        // TODO we have not the commerce code yet
        Oneclick.setOptions(new Options("",
                "579B532A7440BB0C9079DED94D31EA1615BACEB56610332264630D42D0A36B1C", IntegrationType.TEST));
    }

    public static Options buildMallOptions(Options options){
        // set default options for Oneclick mall if options are not configured yet
        if (Options.isEmpty(options) && Options.isEmpty(Oneclick.getOptions()))
            return buildOptionsForTestingOneclickMall();

        return Oneclick.getOptions().buildOptions(options);
    }

    public static class Inscription extends WebpayApiResource {
        public static StartOneclickInscriptionResponse start(String username, String email, String responseUrl)
        throws InscriptionStartException{
            return Oneclick.Inscription.start(username, email, responseUrl, null);
        }

        public static StartOneclickInscriptionResponse start(
                String username, String email, String responseUrl, Options options)
                throws InscriptionStartException{
            try {
                options = Oneclick.buildMallOptions(options);

                final URL endpoint = new URL(getCurrentIntegrationTypeUrl(options.getIntegrationType(), "inscriptions"));

                final InscriptionStartResponse out = WebpayApiResource.getHttpUtil().request(
                        endpoint,
                        HttpUtil.RequestMethod.POST,
                        new InscriptionStartRequest(username, email, responseUrl),
                        WebpayApiResource.buildHeaders(options),
                        InscriptionStartResponse.class);

                if (null == out)
                    throw new InscriptionStartException("Could not obtain response from transbank webservice.");

                if (null != out.getErrorMessage())
                    throw new InscriptionStartException(out.getErrorMessage());

                return new StartOneclickInscriptionResponse(out.getToken(), out.getUrlWebpay());

            } catch (InscriptionStartException ise){
                throw ise;
            } catch (Exception e){
                throw new InscriptionStartException(e);
            }
        }
    }

    public static void main(String[] args) throws InscriptionStartException{
        System.out.println("---------------------------- Oneclick [Inscription.start] ----------------------------");
        Oneclick.configureMallForTesting();
        final StartOneclickInscriptionResponse inscriptionStart =
                Inscription.start("Nombreusuario", "correo@casdasd.cl", "http://some.url/return");
        System.out.println(inscriptionStart);
    }
}
