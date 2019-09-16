package cl.transbank.patpass;

import cl.transbank.common.IntegrationType;
import cl.transbank.common.IntegrationTypeHelper;
import cl.transbank.common.Options;
import cl.transbank.exception.TransbankException;
import cl.transbank.model.WebpayApiRequest;
import cl.transbank.patpass.model.PatpassComercioInscriptionStartResponse;
import cl.transbank.patpass.model.PatpassComercioTransactionStatusResponse;
import cl.transbank.util.HttpUtil;
import cl.transbank.webpay.WebpayApiResource;
import cl.transbank.webpay.exception.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Logger;

public class PatpassComercio {
    private static Logger logger = Logger.getLogger(PatpassComercio.class.getName());

    @Setter(AccessLevel.PRIVATE)
    @Getter(AccessLevel.PRIVATE)
    private static Options patpassOptions = new PatpassOptions();

    public static String getCurrentIntegrationTypeUrl(IntegrationType integrationType) {
        if (null == integrationType)
            integrationType = IntegrationType.TEST;

        return String.format(
                "%s/restpatpass/v1/services",
                IntegrationTypeHelper.getPatpassIntegrationType(integrationType));

    }

    public static void setCommerceCode(String commerceCode) {
        PatpassComercio.getPatpassOptions().setCommerceCode(commerceCode);
    }

    public static String getCommerceCode() {
        return PatpassComercio.getPatpassOptions().getCommerceCode();
    }

    public static void setApiKey(String apiKey) {
        PatpassComercio.getPatpassOptions().setApiKey(apiKey);
    }

    public static String getApiKey() {
        return PatpassComercio.getPatpassOptions().getApiKey();
    }

    public static void setIntegrationType(IntegrationType integrationType) {
        PatpassComercio.getPatpassOptions().setIntegrationType(integrationType);
    }

    public static IntegrationType getIntegrationType() {
        return PatpassComercio.getPatpassOptions().getIntegrationType();
    }

    public static Options buildOptionsForTestingPatpassComercio() {
        return new PatpassOptions("28299257",
                "cxxXQgGD9vrVe4M41FIt", IntegrationType.TEST);
    }

    public static Options buildMallOptions(Options patpassOptions) {
        // set default options for PatpassComercio  if options are not configured yet
        if (Options.isEmpty(patpassOptions) && Options.isEmpty(PatpassComercio.getPatpassOptions()))
            return buildOptionsForTestingPatpassComercio();

        return PatpassComercio.getPatpassOptions().buildOptions(patpassOptions);
    }

    public static class Inscription {
        public static PatpassComercioInscriptionStartResponse start(String url,
                                                                    String name,
                                                                    String firstLastName,
                                                                    String secondLastName,
                                                                    String rut,
                                                                    String serviceId,
                                                                    String finalUrl,
                                                                    String maxAmount,
                                                                    String phoneNumber,
                                                                    String mobileNumber,
                                                                    String patpassName,
                                                                    String personEmail,
                                                                    String commerceEmail,
                                                                    String address,
                                                                    String city) throws IOException, InscriptionStartException {
            return PatpassComercio.Inscription.start(url, name, firstLastName, secondLastName, rut, serviceId, finalUrl, maxAmount,
                    phoneNumber, mobileNumber, patpassName, personEmail, commerceEmail, address, city, null);
        }

        public static PatpassComercioInscriptionStartResponse start(String url,
                                                                    String name,
                                                                    String firstLastName,
                                                                    String secondLastName,
                                                                    String rut,
                                                                    String serviceId,
                                                                    String finalUrl,
                                                                    String maxAmount,
                                                                    String phoneNumber,
                                                                    String mobileNumber,
                                                                    String patpassName,
                                                                    String personEmail,
                                                                    String commerceEmail,
                                                                    String address,
                                                                    String city,
                                                                    Options patpassOptions) throws IOException, InscriptionStartException {
            patpassOptions = PatpassComercio.buildMallOptions(patpassOptions);
            final URL endpoint = new URL(String.format("%s/patInscription", PatpassComercio.getCurrentIntegrationTypeUrl(patpassOptions.getIntegrationType())));
            final WebpayApiRequest request = new PatpassComercioInscriptionStartRequest(url, name, firstLastName, secondLastName, rut, serviceId, finalUrl, patpassOptions.getCommerceCode(), maxAmount,
                    phoneNumber, mobileNumber, patpassName, personEmail, commerceEmail, address, city);
            try {
                return WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.POST, request, patpassOptions, PatpassComercioInscriptionStartResponse.class);
            } catch (TransbankException e) {
                throw new InscriptionStartException(e);
            }
        }
    }

    public static class Transaction {

        public static PatpassComercioTransactionStatusResponse status(String token) throws IOException, TransactionStatusException {
            return PatpassComercio.Transaction.status(token, null);
        }

        public static PatpassComercioTransactionStatusResponse status(String token, Options options) throws IOException, TransactionStatusException {
            options = PatpassComercio.buildMallOptions(options);
            final URL endpoint = new URL(String.format("%s/status", PatpassComercio.getCurrentIntegrationTypeUrl(options.getIntegrationType())));
            final WebpayApiRequest request = new PatpassComercioTransactionStatusRequest(token);

            try {
                return WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.POST, request, options, PatpassComercioTransactionStatusResponse.class);
            } catch (TransbankException e) {
                throw new TransactionStatusException(e);
            }
        }
    }
}
