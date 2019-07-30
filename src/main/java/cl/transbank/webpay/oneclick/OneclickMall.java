package cl.transbank.webpay.oneclick;

import cl.transbank.util.HttpUtil;
import cl.transbank.webpay.IntegrationType;
import cl.transbank.webpay.Options;
import cl.transbank.webpay.WebpayApiResource;
import cl.transbank.webpay.exception.*;
import cl.transbank.model.WebpayApiRequest;
import cl.transbank.webpay.oneclick.model.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.net.URL;
import java.util.logging.Logger;

public class OneclickMall {
    private static Logger logger = Logger.getLogger(OneclickMall.class.getName());

    @Setter(AccessLevel.PRIVATE) @Getter(AccessLevel.PRIVATE) private static Options options = new Options();

    public static String getCurrentIntegrationTypeUrl(IntegrationType integrationType) {
        if (null == integrationType)
            integrationType = IntegrationType.TEST;

        return String.format(
                "%s/rswebpaytransaction/api/oneclick/v1.0",
                integrationType.getApiBase());

    }

    public static void setCommerceCode(String commerceCode) {
        OneclickMall.getOptions().setCommerceCode(commerceCode);
    }

    public static String getCommerceCode() {
        return OneclickMall.getOptions().getCommerceCode();
    }

    public static void setApiKey(String apiKey) {
        OneclickMall.getOptions().setApiKey(apiKey);
    }

    public static String getApiKey() {
        return OneclickMall.getOptions().getApiKey();
    }

    public static void setIntegrationType(IntegrationType integrationType) {
        OneclickMall.getOptions().setIntegrationType(integrationType);
    }

    public static IntegrationType getIntegrationType() {
        return OneclickMall.getOptions().getIntegrationType();
    }

    public static Options buildOptionsForTestingOneclickMall(){
        return new Options ("597055555541",
                "579B532A7440BB0C9079DED94D31EA1615BACEB56610332264630D42D0A36B1C", IntegrationType.TEST);
    }

    public static Options buildMallOptions(Options options) {
        // set default options for OneclickMall mall if options are not configured yet
        if (Options.isEmpty(options) && Options.isEmpty(OneclickMall.getOptions()))
            return buildOptionsForTestingOneclickMall();

        return OneclickMall.getOptions().buildOptions(options);
    }

    public static class Inscription {
        public static StartOneclickMallInscriptionResponse start(String username, String email, String responseUrl) throws StartInscriptionException {
            return OneclickMall.Inscription.start(username, email, responseUrl, null);
        }

        public static StartOneclickMallInscriptionResponse start(String username, String email, String responseUrl, Options options) throws StartInscriptionException {
            try {
                options = OneclickMall.buildMallOptions(options);
                final URL endpoint = new URL(String.format("%s/inscriptions", OneclickMall.getCurrentIntegrationTypeUrl(options.getIntegrationType())));
                final WebpayApiRequest request = new InscriptionStartRequest(username, email, responseUrl);
                return WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.POST, request, options, StartOneclickMallInscriptionResponse.class);
            } catch (Exception e){
                throw new StartInscriptionException(e);
            }
        }

        public static FinishOneclickMallInscriptionResponse finish(String token) throws FinishInscriptionException {
           return OneclickMall.Inscription.finish(token, null);
        }

        public static FinishOneclickMallInscriptionResponse finish(String token, Options options) throws FinishInscriptionException {
            try {
                options = OneclickMall.buildMallOptions(options);
                final URL endpoint = new URL(String.format("%s/inscriptions/%s", OneclickMall.getCurrentIntegrationTypeUrl(options.getIntegrationType()), token));
                return WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.PUT, options, FinishOneclickMallInscriptionResponse.class);
            } catch (Exception e) {
                throw new FinishInscriptionException(e);
            }
        }

        public static void delete(String username, String tbkUser) throws DeleteInscriptionException {
            OneclickMall.Inscription.delete(username, tbkUser, null);
        }

        public static void delete(String username, String tbkUser, Options options) throws DeleteInscriptionException {
            try {
                options = OneclickMall.buildMallOptions(options);
                final URL endpoint = new URL(String.format("%s/inscriptions", OneclickMall.getCurrentIntegrationTypeUrl(options.getIntegrationType())));
                WebpayApiRequest request = new DeleteInscriptionRequest(username, tbkUser);
                WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.DELETE, request, options);
            } catch (Exception e) {
                throw new DeleteInscriptionException(e);
            }
        }
    }

    public static class Transaction {
        public static AuthorizeOneclickMallTransactionResponse authorize(String username, String tbkUser, String buyOrder, CreateMallTransactionDetails details) throws AuthorizeTransactionException {
            return OneclickMall.Transaction.authorize(username, tbkUser, buyOrder, details,null);
        }

        public static AuthorizeOneclickMallTransactionResponse authorize(String username, String tbkUser, String buyOrder, CreateMallTransactionDetails details, Options options) throws AuthorizeTransactionException {
            try {
                options = OneclickMall.buildMallOptions(options);
                final URL endpoint = new URL(String.format("%s/transactions", OneclickMall.getCurrentIntegrationTypeUrl(options.getIntegrationType())));
                WebpayApiRequest request = new AuthorizeTransactionRequest(username, tbkUser, buyOrder, details.getDetails());
                return WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.POST, request, options, AuthorizeOneclickMallTransactionResponse.class);
            } catch (Exception e) {
                throw new AuthorizeTransactionException(e);
            }
        }

        public static RefundOneclickMallTransactionResponse refund(String buyOrder, String childCommerceCode, String childBuyOrder, double amount) throws RefundTransactionException {
            return OneclickMall.Transaction.refund(buyOrder, childCommerceCode, childBuyOrder, amount, null);
        }

        public static RefundOneclickMallTransactionResponse refund(String buyOrder, String childCommerceCode, String childBuyOrder, double amount, Options options) throws RefundTransactionException {
            try {
                options = OneclickMall.buildMallOptions(options);
                final URL endpoint = new URL(String.format("%s/transactions/%s/refunds", OneclickMall.getCurrentIntegrationTypeUrl(options.getIntegrationType()), buyOrder));
                WebpayApiRequest request = new RefundMallTransactionRequest(childCommerceCode, childBuyOrder, amount);
                return WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.POST, request, options, RefundOneclickMallTransactionResponse.class);
            } catch (Exception e) {
                throw new RefundTransactionException(e);
            }
        }

        public static StatusOneclickMallTransactionResponse status(String buyOrder) throws StatusTransactionException {
            return OneclickMall.Transaction.status(buyOrder, null);
        }

        public static StatusOneclickMallTransactionResponse status(String buyOrder, Options options) throws StatusTransactionException {
            try {
                options = OneclickMall.buildMallOptions(options);
                final URL endpoint = new URL(String.format("%s/transactions/%s", OneclickMall.getCurrentIntegrationTypeUrl(options.getIntegrationType()), buyOrder));
                return WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.GET, options, StatusOneclickMallTransactionResponse.class);
            } catch (Exception e) {
                throw new StatusTransactionException(e);
            }
        }
    }
}
