package cl.transbank.webpay.oneclick;

import cl.transbank.util.HttpUtil;
import cl.transbank.webpay.IntegrationType;
import cl.transbank.webpay.Options;
import cl.transbank.webpay.WebpayApiResource;
import cl.transbank.webpay.exception.*;
import cl.transbank.webpay.model.WebpayApiRequest;
import cl.transbank.webpay.oneclick.model.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.net.URL;
import java.util.Random;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OneclickMallDeferred {
    private static Logger logger = Logger.getLogger(OneclickMallDeferred.class.getName());

    @Setter(AccessLevel.PRIVATE) @Getter(AccessLevel.PRIVATE) private static Options options = new Options();

    public static String getCurrentIntegrationTypeUrl(IntegrationType integrationType) {
        if (null == integrationType)
            integrationType = IntegrationType.TEST;

        return String.format(
                "%s/rswebpaytransaction/api/oneclick/v1.0",
                integrationType.getApiBase());

    }

    public static void setCommerceCode(String commerceCode) {
        OneclickMallDeferred.getOptions().setCommerceCode(commerceCode);
    }

    public static String getCommerceCode() {
        return OneclickMallDeferred.getOptions().getCommerceCode();
    }

    public static void setApiKey(String apiKey) {
        OneclickMallDeferred.getOptions().setApiKey(apiKey);
    }

    public static String getApiKey() {
        return OneclickMallDeferred.getOptions().getApiKey();
    }

    public static void setIntegrationType(IntegrationType integrationType) {
        OneclickMallDeferred.getOptions().setIntegrationType(integrationType);
    }

    public static IntegrationType getIntegrationType() {
        return OneclickMallDeferred.getOptions().getIntegrationType();
    }

    public static Options buildOptionsForTestingOneclickMallDeferred(){
        return new Options ("597055555547", // TODO WE NEED A COMMERCE CODE HERE!!!
                "579B532A7440BB0C9079DED94D31EA1615BACEB56610332264630D42D0A36B1C", IntegrationType.TEST);
    }

    public static Options buildMallDeferredOptions(Options options) {
        // set default options for OneclickMall mall if options are not configured yet
        if (Options.isEmpty(options) && Options.isEmpty(OneclickMallDeferred.getOptions()))
            return buildOptionsForTestingOneclickMallDeferred();

        return OneclickMallDeferred.getOptions().buildOptions(options);
    }

    public static class Inscription {
        public static StartOneclickMallInscriptionResponse start(String username, String email, String responseUrl) throws StartInscriptionException {
            return OneclickMallDeferred.Inscription.start(username, email, responseUrl, null);
        }

        public static StartOneclickMallInscriptionResponse start(String username, String email, String responseUrl, Options options) throws StartInscriptionException {
            options = OneclickMallDeferred.buildMallDeferredOptions(options);
            return OneclickMall.Inscription.start(username, email, responseUrl, options);
        }

        public static FinishOneclickMallInscriptionResponse finish(String token) throws FinishInscriptionException {
            return OneclickMallDeferred.Inscription.finish(token, null);
        }

        public static FinishOneclickMallInscriptionResponse finish(String token, Options options) throws FinishInscriptionException {
            options = OneclickMallDeferred.buildMallDeferredOptions(options);
            return OneclickMall.Inscription.finish(token, options);
        }

        public static void delete(String username, String tbkUser) throws DeleteInscriptionException {
            OneclickMallDeferred.Inscription.delete(username, tbkUser, null);
        }

        public static void delete(String username, String tbkUser, Options options) throws DeleteInscriptionException {
            options = OneclickMallDeferred.buildMallDeferredOptions(options);
            OneclickMall.Inscription.delete(username, tbkUser, options);
        }
    }

    public static class Transaction {
        public static AuthorizeOneclickMallTransactionResponse authorize(String username, String tbkUser, String buyOrder, CreateMallTransactionDetails details) throws AuthorizeTransactionException {
            return OneclickMallDeferred.Transaction.authorize(username, tbkUser, buyOrder, details,null);
        }

        public static AuthorizeOneclickMallTransactionResponse authorize(String username, String tbkUser, String buyOrder, CreateMallTransactionDetails details, Options options) throws AuthorizeTransactionException {
            options = OneclickMallDeferred.buildMallDeferredOptions(options);
            return OneclickMall.Transaction.authorize(username, tbkUser, buyOrder, details,options);
        }

        public static RefundOneclickMallTransactionResponse refund(String buyOrder, String childCommerceCode, String childBuyOrder, double amount) throws RefundTransactionException {
            return OneclickMallDeferred.Transaction.refund(buyOrder, childCommerceCode, childBuyOrder, amount, null);
        }

        public static RefundOneclickMallTransactionResponse refund(String buyOrder, String childCommerceCode, String childBuyOrder, double amount, Options options) throws RefundTransactionException {
            options = OneclickMallDeferred.buildMallDeferredOptions(options);
            return OneclickMall.Transaction.refund(buyOrder, childCommerceCode, childBuyOrder, amount, options);
        }

        public static StatusOneclickMallTransactionResponse status(String buyOrder) throws StatusTransactionException {
            return OneclickMallDeferred.Transaction.status(buyOrder, null);
        }

        public static StatusOneclickMallTransactionResponse status(String buyOrder, Options options) throws StatusTransactionException {
            options = OneclickMallDeferred.buildMallDeferredOptions(options);
            return OneclickMall.Transaction.status(buyOrder, options);
        }

        public static CaptureOneclickMallTransactionResponse capture(String buyOrder) throws CaptureTransactionException { //TODO PARAMS SHOULD BE EDITED WHEN WE KNOW PARAMS FOR THIS
            return OneclickMallDeferred.Transaction.capture(buyOrder, null);
        }

        public static CaptureOneclickMallTransactionResponse capture(String buyOrder, Options options) throws CaptureTransactionException { //TODO PARAMS SHOULD BE EDITED WHEN WE KNOW PARAMS FOR THIS
            try {
                options = OneclickMallDeferred.buildMallDeferredOptions(options);
                final URL endpoint = new URL(String.format("%s/transactions/%s/capture", OneclickMallDeferred.getCurrentIntegrationTypeUrl(options.getIntegrationType()), buyOrder)); // TODO VALIDATE THIS URL WHEN WE HAVE DOC ABOUT IT
                WebpayApiRequest request = new CaptureTransactionRequest();
                return WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.PUT, request, options, CaptureOneclickMallTransactionResponse.class);
            } catch (Exception e) {
                throw new CaptureTransactionException(e);
            }
        }
    }
}
