package cl.transbank.webpay.webpayplus;

import cl.transbank.util.HttpUtil;
import cl.transbank.webpay.IntegrationType;
import cl.transbank.webpay.Options;
import cl.transbank.webpay.WebpayApiResource;
import cl.transbank.webpay.exception.*;
import cl.transbank.webpay.model.WebpayApiRequest;
import cl.transbank.webpay.webpayplus.model.*;
import lombok.AccessLevel;
import lombok.Getter;

import java.net.URL;
import java.util.logging.*;

public class WebpayPlus {
    private static Logger logger = Logger.getLogger(WebpayPlus.class.getName());

    public static String getCurrentIntegrationTypeUrl(IntegrationType integrationType) {
        if (null == integrationType)
            integrationType = IntegrationType.TEST;

        return String.format(
                "%s/rswebpaytransaction/api/webpay/v1.0/transactions",
                integrationType.getApiBase());

    }

    private static <T> T capture(
            String token, String commerceCode, String buyOrder, String authorizationCode, double amount, Class<T> returnType, Options options)
            throws CaptureTransactionException {
        try {
            final URL endpoint = new URL(String.format("%s/%s/capture", WebpayPlus.getCurrentIntegrationTypeUrl(options.getIntegrationType()), token));
            final WebpayApiRequest request = new TransactionCaptureRequest(commerceCode, buyOrder, authorizationCode, amount);
            return WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.PUT, request, options, returnType);
        } catch (Exception e) {
            throw new CaptureTransactionException(e);
        }
    }

    public static class Transaction {
        @Getter(AccessLevel.PRIVATE) private static Options options = new Options();

        public static void setCommerceCode(String commerceCode) {
            WebpayPlus.Transaction.getOptions().setCommerceCode(commerceCode);
        }

        public static String getCommerceCode() {
            return WebpayPlus.Transaction.getOptions().getCommerceCode();
        }

        public static void setApiKey(String apiKey) {
            WebpayPlus.Transaction.getOptions().setApiKey(apiKey);
        }

        public static String getApiKey() {
            return WebpayPlus.Transaction.getOptions().getApiKey();
        }

        public static void setIntegrationType(IntegrationType integrationType) {
            WebpayPlus.Transaction.getOptions().setIntegrationType(integrationType);
        }

        public static IntegrationType getIntegrationType() {
            return WebpayPlus.Transaction.getOptions().getIntegrationType();
        }

        public static Options buildOptionsForTesting() {
            return new Options(
                    "597055555532", "579B532A7440BB0C9079DED94D31EA1615BACEB56610332264630D42D0A36B1C", IntegrationType.TEST);
        }

        private static Options builOptions(Options options) {
            // set default options for Webpay Plus Normal if options are not configured yet
            if (Options.isEmpty(options) && Options.isEmpty(WebpayPlus.Transaction.getOptions()))
                return WebpayPlus.Transaction.buildOptionsForTesting();

            return WebpayPlus.Transaction.getOptions().buildOptions(options);
        }

        public static CreateWebpayPlusTransactionResponse create(
                String buyOrder, String sessionId, double amount, String returnUrl) throws CreateTransactionException {
            return WebpayPlus.Transaction.create(buyOrder, sessionId, amount, returnUrl, null);
        }

        public static CreateWebpayPlusTransactionResponse create(
                String buyOrder, String sessionId, double amount, String returnUrl, Options options) throws CreateTransactionException {
            try {
                options = WebpayPlus.Transaction.builOptions(options);
                final URL endpoint = new URL(getCurrentIntegrationTypeUrl(options.getIntegrationType()));
                final WebpayApiRequest request = new TransactionCreateRequest(buyOrder, sessionId, amount, returnUrl);
                return WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.POST, request, options, CreateWebpayPlusTransactionResponse.class);
            } catch (Exception e) {
                throw new CreateTransactionException(e);
            }
        }

        public static CommitWebpayPlusTransactionResponse commit(String token) throws CommitTransactionException {
            return WebpayPlus.Transaction.commit(token, null);
        }

        public static CommitWebpayPlusTransactionResponse commit(String token, Options options)
                throws CommitTransactionException {
            try {
                options = WebpayPlus.Transaction.builOptions(options);
                final URL endpoint = new URL(String.format("%s/%s", WebpayPlus.getCurrentIntegrationTypeUrl(options.getIntegrationType()), token));
                return WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.PUT, options, CommitWebpayPlusTransactionResponse.class);
            } catch (Exception e) {
                throw new CommitTransactionException(e);
            }
        }

        public static RefundWebpayPlusTransactionResponse refund(String token, double amount)
                throws RefundTransactionException {
            return WebpayPlus.Transaction.refund(token, amount, null);
        }

        public static RefundWebpayPlusTransactionResponse refund(String token, double amount, Options options)
                throws RefundTransactionException {
            try {
                options = WebpayPlus.Transaction.builOptions(options);
                final URL endpoint = new URL(String.format("%s/%s/refunds", WebpayPlus.getCurrentIntegrationTypeUrl(options.getIntegrationType()), token));
                final WebpayApiRequest request = new TransactionRefundRequest(amount);
                return WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.POST, request, options, RefundWebpayPlusTransactionResponse.class);
            } catch (Exception e) {
                throw new RefundTransactionException(e);
            }
        }

        public static StatusWebpayPlusTransactionResponse status(String token) throws StatusTransactionException {
            return WebpayPlus.Transaction.status(token, null);
        }

        public static StatusWebpayPlusTransactionResponse status(String token, Options options)
                throws StatusTransactionException {
            try {
                options = WebpayPlus.Transaction.builOptions(options);
                final URL endpoint = new URL(String.format("%s/%s", WebpayPlus.getCurrentIntegrationTypeUrl(options.getIntegrationType()), token));
                return WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.GET, options, StatusWebpayPlusTransactionResponse.class);
            } catch (Exception e) {
                throw new StatusTransactionException(e);
            }
        }
    }

    public static class DeferredTransaction {
        @Getter(AccessLevel.PRIVATE) private static Options options = new Options();

        public static void setCommerceCode(String commerceCode) {
            WebpayPlus.DeferredTransaction.getOptions().setCommerceCode(commerceCode);
        }

        public static String getCommerceCode() {
            return WebpayPlus.DeferredTransaction.getOptions().getCommerceCode();
        }

        public static void setApiKey(String apiKey) {
            WebpayPlus.DeferredTransaction.getOptions().setApiKey(apiKey);
        }

        public static String getApiKey() {
            return WebpayPlus.DeferredTransaction.getOptions().getApiKey();
        }

        public static void setIntegrationType(IntegrationType integrationType) {
            WebpayPlus.DeferredTransaction.getOptions().setIntegrationType(integrationType);
        }

        public static IntegrationType getIntegrationType() {
            return WebpayPlus.DeferredTransaction.getOptions().getIntegrationType();
        }

        public static Options buildOptionsForTesting() {
            return new Options(
                    "597055555540", "579B532A7440BB0C9079DED94D31EA1615BACEB56610332264630D42D0A36B1C", IntegrationType.TEST);
        }

        private static Options builOptions(Options options) {
            // set default options for Webpay Plus Normal if options are not configured yet
            if (Options.isEmpty(options) && Options.isEmpty(WebpayPlus.DeferredTransaction.getOptions()))
                return WebpayPlus.DeferredTransaction.buildOptionsForTesting();

            return WebpayPlus.DeferredTransaction.getOptions().buildOptions(options);
        }

        public static CreateWebpayPlusTransactionResponse create(
                String buyOrder, String sessionId, double amount, String returnUrl) throws CreateTransactionException {
            return WebpayPlus.DeferredTransaction.create(buyOrder, sessionId, amount, returnUrl, null);
        }

        public static CreateWebpayPlusTransactionResponse create(
                String buyOrder, String sessionId, double amount, String returnUrl, Options options) throws CreateTransactionException {
            options = WebpayPlus.DeferredTransaction.builOptions(options);
            return WebpayPlus.Transaction.create(buyOrder, sessionId, amount, returnUrl, options);
        }

        public static CommitWebpayPlusTransactionResponse commit(String token) throws CommitTransactionException {
            return WebpayPlus.DeferredTransaction.commit(token, null);
        }

        public static CommitWebpayPlusTransactionResponse commit(String token, Options options)
                throws CommitTransactionException {
            options = WebpayPlus.DeferredTransaction.builOptions(options);
            return WebpayPlus.Transaction.commit(token, options);
        }

        public static RefundWebpayPlusTransactionResponse refund(String token, double amount)
                throws RefundTransactionException {
            return WebpayPlus.DeferredTransaction.refund(token, amount, null);
        }

        public static RefundWebpayPlusTransactionResponse refund(String token, double amount, Options options)
                throws RefundTransactionException {
            options = WebpayPlus.DeferredTransaction.builOptions(options);
            return WebpayPlus.Transaction.refund(token, amount, options);
        }

        public static StatusWebpayPlusTransactionResponse status(String token) throws StatusTransactionException {
            return WebpayPlus.DeferredTransaction.status(token, null);
        }

        public static StatusWebpayPlusTransactionResponse status(String token, Options options)
                throws StatusTransactionException {
            options = WebpayPlus.DeferredTransaction.builOptions(options);
            return WebpayPlus.Transaction.status(token, options);
        }

        public static CaptureWebpayPlusTransactionResponse capture(
                String token, String buyOrder, String authorizationCode, double amount) throws CaptureTransactionException {
            return WebpayPlus.DeferredTransaction.capture(token, buyOrder, authorizationCode, amount, null);
        }

        public static CaptureWebpayPlusTransactionResponse capture(
                String token, String buyOrder, String authorizationCode, double amount, Options options)
                throws CaptureTransactionException {
            options = WebpayPlus.DeferredTransaction.builOptions(options);
            return WebpayPlus.capture(token, null, buyOrder, authorizationCode, amount, CaptureWebpayPlusTransactionResponse.class, options);
        }
    }

    public static class MallTransaction {
        @Getter(AccessLevel.PRIVATE) private static Options options = new Options();

        public static void setCommerceCode(String commerceCode) {
            WebpayPlus.MallTransaction.getOptions().setCommerceCode(commerceCode);
        }

        public static String getCommerceCode() {
            return WebpayPlus.MallTransaction.getOptions().getCommerceCode();
        }

        public static void setApiKey(String apiKey) {
            WebpayPlus.MallTransaction.getOptions().setApiKey(apiKey);
        }

        public static String getApiKey() {
            return WebpayPlus.MallTransaction.getOptions().getApiKey();
        }

        public static void setIntegrationType(IntegrationType integrationType) {
            WebpayPlus.MallTransaction.getOptions().setIntegrationType(integrationType);
        }

        public static IntegrationType getIntegrationType() {
            return WebpayPlus.MallTransaction.getOptions().getIntegrationType();
        }

        public static Options buildOptionsForTesting() {
            return new Options(
                    "597055555535", "579B532A7440BB0C9079DED94D31EA1615BACEB56610332264630D42D0A36B1C", IntegrationType.TEST);
        }

        private static Options builOptions(Options options) {
            // set default options for Webpay Plus Normal if options are not configured yet
            if (Options.isEmpty(options) && Options.isEmpty(WebpayPlus.MallTransaction.getOptions()))
                return WebpayPlus.MallTransaction.buildOptionsForTesting();

            return WebpayPlus.MallTransaction.getOptions().buildOptions(options);
        }

        public static CreateWebpayPlusMallTransactionResponse create (
                String buyOrder, String sessionId, String returnUrl, CreateMallTransactionDetails details) throws CreateTransactionException {
            return MallTransaction.create (buyOrder, sessionId, returnUrl, details, null);
        }

        public static CreateWebpayPlusMallTransactionResponse create (
                String buyOrder, String sessionId, String returnUrl, CreateMallTransactionDetails details, Options options) throws CreateTransactionException {
            try {
                options = WebpayPlus.MallTransaction.builOptions(options);
                final URL endpoint = new URL(getCurrentIntegrationTypeUrl(options.getIntegrationType()));
                WebpayApiRequest request = new CreateMallTransactionRequest(buyOrder, sessionId, returnUrl, details.getDetails());
                return WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.POST, request, options, CreateWebpayPlusMallTransactionResponse.class);
            } catch (Exception e) {
                throw new CreateTransactionException(e);
            }
        }

        public static CommitWebpayPlusMallTransactionResponse commit(String token) throws CommitTransactionException {
            return WebpayPlus.MallTransaction.commit(token, null);
        }

        public static CommitWebpayPlusMallTransactionResponse commit(String token, Options options)
                throws CommitTransactionException {
            try {
                options = WebpayPlus.MallTransaction.builOptions(options);
                final URL endpoint = new URL(String.format("%s/%s", WebpayPlus.getCurrentIntegrationTypeUrl(options.getIntegrationType()), token));
                return WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.PUT, options, CommitWebpayPlusMallTransactionResponse.class);
            } catch (Exception e) {
                throw new CommitTransactionException(e);
            }
        }

        public static RefundWebpayPlusMallTransactionResponse refund(String token, String buyOrder, String commerceCode, double amount)
                throws RefundTransactionException {
            return WebpayPlus.MallTransaction.refund(token, buyOrder, commerceCode, amount, null);
        }

        public static RefundWebpayPlusMallTransactionResponse refund(String token, String buyOrder, String commerceCode, double amount, Options options)
                throws RefundTransactionException {
            try {
                options = WebpayPlus.MallTransaction.builOptions(options);
                final URL endpoint = new URL(String.format("%s/%s/refunds", WebpayPlus.getCurrentIntegrationTypeUrl(options.getIntegrationType()), token));
                final WebpayApiRequest request = new TransactionRefundRequest(buyOrder, commerceCode, amount);
                return WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.POST, request, options, RefundWebpayPlusMallTransactionResponse.class);
            } catch (Exception e) {
                throw new RefundTransactionException(e);
            }
        }

        public static StatusWebpayPlusMallTransactionResponse status(String token) throws StatusTransactionException {
            return WebpayPlus.MallTransaction.status(token, null);
        }

        public static StatusWebpayPlusMallTransactionResponse status(String token, Options options)
                throws StatusTransactionException {
            try {
                options = WebpayPlus.MallTransaction.builOptions(options);
                final URL endpoint = new URL(String.format("%s/%s", WebpayPlus.getCurrentIntegrationTypeUrl(options.getIntegrationType()), token));
                return WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.GET, options, StatusWebpayPlusMallTransactionResponse.class);
            } catch (Exception e) {
                throw new StatusTransactionException(e);
            }
        }
    }

    public static class MallDeferredTransaction {
        @Getter(AccessLevel.PRIVATE) private static Options options = new Options();

        public static void setCommerceCode(String commerceCode) {
            WebpayPlus.MallDeferredTransaction.getOptions().setCommerceCode(commerceCode);
        }

        public static String getCommerceCode() {
            return WebpayPlus.MallDeferredTransaction.getOptions().getCommerceCode();
        }

        public static void setApiKey(String apiKey) {
            WebpayPlus.MallDeferredTransaction.getOptions().setApiKey(apiKey);
        }

        public static String getApiKey() {
            return WebpayPlus.MallDeferredTransaction.getOptions().getApiKey();
        }

        public static void setIntegrationType(IntegrationType integrationType) {
            WebpayPlus.MallDeferredTransaction.getOptions().setIntegrationType(integrationType);
        }

        public static IntegrationType getIntegrationType() {
            return WebpayPlus.MallDeferredTransaction.getOptions().getIntegrationType();
        }

        public static Options buildOptionsForTesting() {
            return new Options(
                    "597055555544", "579B532A7440BB0C9079DED94D31EA1615BACEB56610332264630D42D0A36B1C", IntegrationType.TEST);
        }

        private static Options builOptions(Options options) {
            // set default options for Webpay Plus Normal if options are not configured yet
            if (Options.isEmpty(options) && Options.isEmpty(WebpayPlus.MallDeferredTransaction.getOptions()))
                return WebpayPlus.MallDeferredTransaction.buildOptionsForTesting();

            return WebpayPlus.MallDeferredTransaction.getOptions().buildOptions(options);
        }

        public static CreateWebpayPlusMallTransactionResponse create (
                String buyOrder, String sessionId, String returnUrl, CreateMallTransactionDetails details) throws CreateTransactionException {
            return MallDeferredTransaction.create (buyOrder, sessionId, returnUrl, details, null);
        }

        public static CreateWebpayPlusMallTransactionResponse create (
                String buyOrder, String sessionId, String returnUrl, CreateMallTransactionDetails details, Options options) throws CreateTransactionException {
            options = WebpayPlus.MallDeferredTransaction.builOptions(options);
            return WebpayPlus.MallTransaction.create (buyOrder, sessionId, returnUrl, details, options);
        }

        public static CommitWebpayPlusMallTransactionResponse commit(String token) throws CommitTransactionException {
            return WebpayPlus.MallDeferredTransaction.commit(token, null);
        }

        public static CommitWebpayPlusMallTransactionResponse commit(String token, Options options)
                throws CommitTransactionException {
            options = WebpayPlus.MallDeferredTransaction.builOptions(options);
            return WebpayPlus.MallTransaction.commit(token, options);
        }

        public static RefundWebpayPlusMallTransactionResponse refund(String token, String buyOrder, String commerceCode, double amount)
                throws RefundTransactionException {
            return WebpayPlus.MallDeferredTransaction.refund(token, buyOrder, commerceCode, amount, null);
        }

        public static RefundWebpayPlusMallTransactionResponse refund(String token, String buyOrder, String commerceCode, double amount, Options options)
                throws RefundTransactionException {
            options = WebpayPlus.MallDeferredTransaction.builOptions(options);
            return WebpayPlus.MallTransaction.refund(token, buyOrder, commerceCode, amount, options);
        }

        public static StatusWebpayPlusMallTransactionResponse status(String token) throws StatusTransactionException {
            return WebpayPlus.MallDeferredTransaction.status(token, null);
        }

        public static StatusWebpayPlusMallTransactionResponse status(String token, Options options)
                throws StatusTransactionException {
            options = WebpayPlus.MallDeferredTransaction.builOptions(options);
            return WebpayPlus.MallTransaction.status(token, options);
        }

        public static CaptureWebpayPlusMallTransactionResponse capture(
                String token, String commerceCode, String buyOrder, String authorizationCode, double amount) throws CaptureTransactionException {
            return WebpayPlus.MallDeferredTransaction.capture(token, commerceCode, buyOrder, authorizationCode, amount, null);
        }

        public static CaptureWebpayPlusMallTransactionResponse capture(
                String token, String commerceCode, String buyOrder, String authorizationCode, double amount, Options options)
                throws CaptureTransactionException {
            options = WebpayPlus.MallDeferredTransaction.builOptions(options);
            return WebpayPlus.capture(token, commerceCode, buyOrder, authorizationCode, amount, CaptureWebpayPlusMallTransactionResponse.class, options);
        }
    }
}