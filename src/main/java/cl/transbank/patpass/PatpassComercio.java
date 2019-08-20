package cl.transbank.patpass;

import cl.transbank.exception.TransbankException;
import cl.transbank.model.WebpayApiRequest;
import cl.transbank.patpass.model.PatpassComercioInscriptionStartResponse;
import cl.transbank.util.HttpUtil;
import cl.transbank.webpay.WebpayApiResource;
import cl.transbank.webpay.exception.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.net.URL;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PatpassComercio {
    private static Logger logger = Logger.getLogger(PatpassComercio.class.getName());

    @Setter(AccessLevel.PRIVATE) @Getter(AccessLevel.PRIVATE) private static Options options = new Options();

    public static String getCurrentIntegrationTypeUrl(IntegrationType integrationType) {
        if (null == integrationType)
            integrationType = IntegrationType.TEST;

        return String.format(
                "%s/restpatpass/v1/services",
                integrationType.getApiBase());

    }

    public static void setCommerceCode(String commerceCode) {
        PatpassComercio.getOptions().setCommerceCode(commerceCode);
    }

    public static String getCommerceCode() {
        return PatpassComercio.getOptions().getCommerceCode();
    }

    public static void setApiKey(String apiKey) {
        PatpassComercio.getOptions().setApiKey(apiKey);
    }

    public static String getApiKey() {
        return PatpassComercio.getOptions().getApiKey();
    }

    public static void setIntegrationType(IntegrationType integrationType) {
        PatpassComercio.getOptions().setIntegrationType(integrationType);
    }

    public static IntegrationType getIntegrationType() {
        return PatpassComercio.getOptions().getIntegrationType();
    }

    public static Options buildOptionsForTestingPatpassComercio(){
        return new Options ("28299257",
                "cxxXQgGD9vrVe4M41FIt", IntegrationType.LIVE);
    }

    public static Options buildMallOptions(Options options) {
        // set default options for PatpassComercio  if options are not configured yet
        if (Options.isEmpty(options) && Options.isEmpty(PatpassComercio.getOptions()))
            return buildOptionsForTestingPatpassComercio();

        return PatpassComercio.getOptions().buildOptions(options);
    }

    public static class Inscription {
        public static PatpassComercioInscriptionStartResponse start(String url,
                                                                    String name,
                                                                    String firstLastName,
                                                                    String secondLastName,
                                                                    String rut,
                                                                    String serviceId,
                                                                    String finalUrl,
                                                                    String commerceCode,
                                                                    int maxAmount,
                                                                    String phoneNumber,
                                                                    String mobileNumber,
                                                                    String patpassName,
                                                                    String personEmail,
                                                                    String commerceEmail,
                                                                    String address,
                                                                    String city) throws IOException, InscriptionStartException {
            return PatpassComercio.Inscription.start(url, name, firstLastName, secondLastName, rut, serviceId, finalUrl, commerceCode, maxAmount,
                    phoneNumber, mobileNumber, patpassName, personEmail, commerceEmail, address, city, null);
        }

        public static PatpassComercioInscriptionStartResponse start(String url,
                                                                    String name,
                                                                    String firstLastName,
                                                                    String secondLastName,
                                                                    String rut,
                                                                    String serviceId,
                                                                    String finalUrl,
                                                                    String commerceCode,
                                                                    int maxAmount,
                                                                    String phoneNumber,
                                                                    String mobileNumber,
                                                                    String patpassName,
                                                                    String personEmail,
                                                                    String commerceEmail,
                                                                    String address,
                                                                    String city,
                                                                    Options options) throws IOException, InscriptionStartException {
            options = PatpassComercio.buildMallOptions(options);
            final URL endpoint = new URL(String.format("%s/patInscription", PatpassComercio.getCurrentIntegrationTypeUrl(options.getIntegrationType())));
            final WebpayApiRequest request = new PatpassComercioInscriptionStartRequest(url, name, firstLastName, secondLastName, rut, serviceId, finalUrl, commerceCode, maxAmount,
                    phoneNumber, mobileNumber, patpassName, personEmail, commerceEmail, address, city);
            try {
                return WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.POST, request, options, PatpassComercioInscriptionStartResponse.class);
            } catch (TransbankException e) {
                throw new InscriptionStartException(e);
            }
        }
    }

    public static void main(String[] args) {
        System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$tF %1$tT] [%4$-7s] %5$s %n");
        Logger globalLog = Logger.getLogger("cl.transbank");
        globalLog.setUseParentHandlers(false);
        globalLog.addHandler(new ConsoleHandler() {
            {/*setOutputStream(System.out);*/setLevel(Level.ALL);}
        });
        globalLog.setLevel(Level.ALL);

    }
}
