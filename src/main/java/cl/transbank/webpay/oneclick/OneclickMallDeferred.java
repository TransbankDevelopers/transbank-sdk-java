package cl.transbank.webpay.oneclick;

import cl.transbank.exception.TransbankException;
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

import java.io.IOException;
import java.net.URL;
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
        public static OneclickMallInscriptionStartResponse start(
                String username, String email, String responseUrl) throws IOException, InscriptionStartException {
            return OneclickMallDeferred.Inscription.start(username, email, responseUrl, null);
        }

        public static OneclickMallInscriptionStartResponse start(
                String username, String email, String responseUrl, Options options) throws IOException, InscriptionStartException {
            options = OneclickMallDeferred.buildMallDeferredOptions(options);
            return OneclickMall.Inscription.start(username, email, responseUrl, options);
        }

        public static OneclickMallInscriptionFinishResponse finish(String token) throws IOException, InscriptionFinishException {
            return OneclickMallDeferred.Inscription.finish(token, null);
        }

        public static OneclickMallInscriptionFinishResponse finish(String token, Options options) throws IOException, InscriptionFinishException {
            options = OneclickMallDeferred.buildMallDeferredOptions(options);
            return OneclickMall.Inscription.finish(token, options);
        }

        public static void delete(String username, String tbkUser) throws IOException, InscriptionDeleteException {
            OneclickMallDeferred.Inscription.delete(username, tbkUser, null);
        }

        public static void delete(String username, String tbkUser, Options options) throws IOException, InscriptionDeleteException {
            options = OneclickMallDeferred.buildMallDeferredOptions(options);
            OneclickMall.Inscription.delete(username, tbkUser, options);
        }
    }

    public static class Transaction {
        public static OneclickMallTransactionAuthorizeResponse authorize(
                String username, String tbkUser, String buyOrder, MallTransactionCreateDetails details) throws IOException, TransactionAuthorizeException {
            return OneclickMallDeferred.Transaction.authorize(username, tbkUser, buyOrder, details,null);
        }

        public static OneclickMallTransactionAuthorizeResponse authorize(
                String username, String tbkUser, String buyOrder, MallTransactionCreateDetails details, Options options) throws IOException, TransactionAuthorizeException {
            options = OneclickMallDeferred.buildMallDeferredOptions(options);
            return OneclickMall.Transaction.authorize(username, tbkUser, buyOrder, details,options);
        }

        public static OneclickMallTransactionRefundResponse refund(
                String buyOrder, String childCommerceCode, String childBuyOrder, double amount) throws IOException, TransactionRefundException {
            return OneclickMallDeferred.Transaction.refund(buyOrder, childCommerceCode, childBuyOrder, amount, null);
        }

        public static OneclickMallTransactionRefundResponse refund(
                String buyOrder, String childCommerceCode, String childBuyOrder, double amount, Options options) throws IOException, TransactionRefundException {
            options = OneclickMallDeferred.buildMallDeferredOptions(options);
            return OneclickMall.Transaction.refund(buyOrder, childCommerceCode, childBuyOrder, amount, options);
        }

        public static OneclickMallTransactionStatusResponse status(String buyOrder) throws IOException, TransactionStatusException {
            return OneclickMallDeferred.Transaction.status(buyOrder, null);
        }

        public static OneclickMallTransactionStatusResponse status(String buyOrder, Options options) throws IOException, TransactionStatusException {
            options = OneclickMallDeferred.buildMallDeferredOptions(options);
            return OneclickMall.Transaction.status(buyOrder, options);
        }

        public static OneclickMallTransactionCaptureResponse capture(String buyOrder) throws IOException, TransactionCaptureException { //TODO PARAMS SHOULD BE EDITED WHEN WE KNOW PARAMS FOR THIS
            return OneclickMallDeferred.Transaction.capture(buyOrder, null);
        }

        public static OneclickMallTransactionCaptureResponse capture(String buyOrder, Options options) throws IOException, TransactionCaptureException { //TODO PARAMS SHOULD BE EDITED WHEN WE KNOW PARAMS FOR THIS
            options = OneclickMallDeferred.buildMallDeferredOptions(options);
            final URL endpoint = new URL(String.format("%s/transactions/%s/capture", OneclickMallDeferred.getCurrentIntegrationTypeUrl(options.getIntegrationType()), buyOrder)); // TODO VALIDATE THIS URL WHEN WE HAVE DOC ABOUT IT
            WebpayApiRequest request = new TransactionCaptureRequest();
            try {
                return WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.PUT, request, options, OneclickMallTransactionCaptureResponse.class);
            } catch (TransbankException e) {
                throw new TransactionCaptureException(e);
            }
        }
    }
}
