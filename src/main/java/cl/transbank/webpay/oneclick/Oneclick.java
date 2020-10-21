package cl.transbank.webpay.oneclick;

import cl.transbank.common.IntegrationType;
import cl.transbank.common.IntegrationTypeHelper;
import cl.transbank.common.Options;
import cl.transbank.exception.TransbankException;
import cl.transbank.model.WebpayApiRequest;
import cl.transbank.util.HttpUtil;
import cl.transbank.webpay.WebpayApiResource;
import cl.transbank.webpay.WebpayOptions;
import cl.transbank.webpay.exception.*;
import cl.transbank.webpay.oneclick.model.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Logger;

public class Oneclick{
    private static Logger logger = Logger.getLogger(Oneclick.class.getName());

    @Setter(AccessLevel.PRIVATE) @Getter(AccessLevel.PRIVATE) private static Options options = new WebpayOptions();

    public static String getCurrentIntegrationTypeUrl(IntegrationType integrationType) {
        if (null == integrationType)
            integrationType = IntegrationType.TEST;

        return String.format(
                "%s/rswebpaytransaction/api/oneclick/v1.0",
                IntegrationTypeHelper.getWebpayIntegrationType(integrationType));

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
        return new WebpayOptions("597055555541",
                "579B532A7440BB0C9079DED94D31EA1615BACEB56610332264630D42D0A36B1C", IntegrationType.TEST);
    }

    public static Options buildMallOptions(Options options) {
        // set default options for OneclickMall mall if options are not configured yet
        if (Options.isEmpty(options) && Options.isEmpty(Oneclick.getOptions()))
            return buildOptionsForTestingOneclickMall();

        return Oneclick.getOptions().buildOptions(options);
    }

    public static class MallInscription {
        public static OneclickMallInscriptionStartResponse start(String username, String email, String responseUrl) throws IOException, InscriptionStartException {
            return Oneclick.MallInscription.start(username, email, responseUrl, null);
        }

        public static OneclickMallInscriptionStartResponse start(String username, String email, String responseUrl, Options options) throws IOException, InscriptionStartException {
            options = Oneclick.buildMallOptions(options);
            final URL endpoint = new URL(String.format("%s/inscriptions", Oneclick.getCurrentIntegrationTypeUrl(options.getIntegrationType())));
            final WebpayApiRequest request = new InscriptionStartRequest(username, email, responseUrl);
            try {
                return WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.POST, request, options, OneclickMallInscriptionStartResponse.class);
            } catch (TransbankException e) {
                throw new InscriptionStartException(e);
            }
        }

        public static OneclickMallInscriptionFinishResponse finish(String token) throws IOException, InscriptionFinishException {
            return Oneclick.MallInscription.finish(token, null);
        }

        public static OneclickMallInscriptionFinishResponse finish(String token, Options options) throws IOException, InscriptionFinishException {
            options = Oneclick.buildMallOptions(options);
            final URL endpoint = new URL(String.format("%s/inscriptions/%s", Oneclick.getCurrentIntegrationTypeUrl(options.getIntegrationType()), token));

            try {
                return WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.PUT, options, OneclickMallInscriptionFinishResponse.class);
            } catch (TransbankException e) {
                throw new InscriptionFinishException(e);
            }
        }

        public static void delete(String username, String tbkUser) throws IOException, InscriptionDeleteException {
            Oneclick.MallInscription.delete(username, tbkUser, null);
        }

        public static void delete(String username, String tbkUser, Options options) throws IOException, InscriptionDeleteException {
            options = Oneclick.buildMallOptions(options);
            final URL endpoint = new URL(String.format("%s/inscriptions", Oneclick.getCurrentIntegrationTypeUrl(options.getIntegrationType())));
            WebpayApiRequest request = new InscriptionDeleteRequest(username, tbkUser);

            try {
                WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.DELETE, request, options);
            } catch (TransbankException e) {
                throw new InscriptionDeleteException(e);
            }
        }
    }

    public static class MallTransaction {
        public static OneclickMallTransactionAuthorizeResponse authorize(String username, String tbkUser, String buyOrder, MallTransactionCreateDetails details) throws IOException, TransactionAuthorizeException {
            return Oneclick.MallTransaction.authorize(username, tbkUser, buyOrder, details,null);
        }

        public static OneclickMallTransactionAuthorizeResponse authorize(String username, String tbkUser, String buyOrder, MallTransactionCreateDetails details, Options options) throws IOException, TransactionAuthorizeException {
            options = Oneclick.buildMallOptions(options);
            final URL endpoint = new URL(String.format("%s/transactions", Oneclick.getCurrentIntegrationTypeUrl(options.getIntegrationType())));
            WebpayApiRequest request = new TransactionAuthorizeRequest(username, tbkUser, buyOrder, details.getDetails());

            try {
                return WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.POST, request, options, OneclickMallTransactionAuthorizeResponse.class);
            } catch (TransbankException e) {
                throw new TransactionAuthorizeException(e);
            }
        }

        public static OneclickMallTransactionRefundResponse refund(String buyOrder, String childCommerceCode, String childBuyOrder, double amount) throws IOException, TransactionRefundException {
            return Oneclick.MallTransaction.refund(buyOrder, childCommerceCode, childBuyOrder, amount, null);
        }

        public static OneclickMallTransactionRefundResponse refund(String buyOrder, String childCommerceCode, String childBuyOrder, double amount, Options options) throws IOException, TransactionRefundException {
            options = Oneclick.buildMallOptions(options);
            final URL endpoint = new URL(String.format("%s/transactions/%s/refunds", Oneclick.getCurrentIntegrationTypeUrl(options.getIntegrationType()), buyOrder));
            WebpayApiRequest request = new MallTransactionRefundRequest(childCommerceCode, childBuyOrder, amount);

            try {
                return WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.POST, request, options, OneclickMallTransactionRefundResponse.class);
            } catch (TransbankException e) {
                throw new TransactionRefundException(e);
            }
        }

        public static OneclickMallTransactionStatusResponse status(String buyOrder) throws IOException, TransactionStatusException {
            return Oneclick.MallTransaction.status(buyOrder, null);
        }

        public static OneclickMallTransactionStatusResponse status(String buyOrder, Options options) throws IOException, TransactionStatusException {
            options = Oneclick.buildMallOptions(options);
            final URL endpoint = new URL(String.format("%s/transactions/%s", Oneclick.getCurrentIntegrationTypeUrl(options.getIntegrationType()), buyOrder));

            try {
                return WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.GET, options, OneclickMallTransactionStatusResponse.class);
            } catch (TransbankException e) {
                throw new TransactionStatusException(e);
            }
        }
    }
}
