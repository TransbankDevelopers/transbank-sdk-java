package cl.transbank.webpay.webpayplus;

import cl.transbank.exception.TransbankException;
import cl.transbank.util.HttpUtil;
import cl.transbank.webpay.IntegrationType;
import cl.transbank.webpay.Options;
import cl.transbank.webpay.WebpayApiResource;
import cl.transbank.webpay.exception.*;
import cl.transbank.model.WebpayApiRequest;
import cl.transbank.webpay.webpayplus.model.*;
import com.google.gson.annotations.SerializedName;
import lombok.*;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
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
            throws IOException, TransactionCaptureException {
        final URL endpoint = new URL(String.format("%s/%s/capture", WebpayPlus.getCurrentIntegrationTypeUrl(options.getIntegrationType()), token));
        final WebpayApiRequest request = new TransactionCaptureRequest(commerceCode, buyOrder, authorizationCode, amount);
        try {
            return WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.PUT, request, options, returnType);
        } catch (TransbankException e) {
            throw new TransactionCaptureException(e);
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

        private static Options buildOptions(Options options) {
            // set default options for Webpay Plus Normal if options are not configured yet
            if (Options.isEmpty(options) && Options.isEmpty(WebpayPlus.Transaction.getOptions()))
                return WebpayPlus.Transaction.buildOptionsForTesting();

            return WebpayPlus.Transaction.getOptions().buildOptions(options);
        }

        public static WebpayPlusTransactionCreateResponse create(
                String buyOrder, String sessionId, double amount, String returnUrl) throws IOException, TransactionCreateException {
            return WebpayPlus.Transaction.create(buyOrder, sessionId, amount, returnUrl, null);
        }

        public static WebpayPlusTransactionCreateResponse create(
                String buyOrder, String sessionId, double amount, String returnUrl, Options options) throws IOException, TransactionCreateException {
            options = WebpayPlus.Transaction.buildOptions(options);
            final URL endpoint = new URL(getCurrentIntegrationTypeUrl(options.getIntegrationType()));
            final WebpayApiRequest request = new TransactionCreateRequest(buyOrder, sessionId, amount, returnUrl);

            try {
                return WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.POST, request, options, WebpayPlusTransactionCreateResponse.class);
            } catch (TransbankException e) {
                throw new TransactionCreateException(e);
            }
        }

        public static WebpayPlusTransactionCommitResponse commit(String token) throws IOException, TransactionCommitException {
            return WebpayPlus.Transaction.commit(token, null);
        }

        public static WebpayPlusTransactionCommitResponse commit(String token, Options options)
                throws IOException, TransactionCommitException {
            options = WebpayPlus.Transaction.buildOptions(options);
            final URL endpoint = new URL(String.format("%s/%s", WebpayPlus.getCurrentIntegrationTypeUrl(options.getIntegrationType()), token));

            try {
                return WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.PUT, options, WebpayPlusTransactionCommitResponse.class);
            } catch (TransbankException e) {
                throw new TransactionCommitException(e);
            }
        }

        public static WebpayPlusTransactionRefundResponse refund(String token, double amount)
                throws IOException, TransactionRefundException {
            return WebpayPlus.Transaction.refund(token, amount, null);
        }

        public static WebpayPlusTransactionRefundResponse refund(String token, double amount, Options options)
                throws IOException, TransactionRefundException {
            options = WebpayPlus.Transaction.buildOptions(options);
            final URL endpoint = new URL(String.format("%s/%s/refunds", WebpayPlus.getCurrentIntegrationTypeUrl(options.getIntegrationType()), token));
            final WebpayApiRequest request = new TransactionRefundRequest(amount);

            try {
                return WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.POST, request, options, WebpayPlusTransactionRefundResponse.class);
            } catch (TransbankException e) {
                throw new TransactionRefundException(e);
            }
        }

        public static WebpayPlusTransactionStatusResponse status(String token) throws IOException, TransactionStatusException {
            return WebpayPlus.Transaction.status(token, null);
        }

        public static WebpayPlusTransactionStatusResponse status(String token, Options options)
                throws IOException, TransactionStatusException {
            options = WebpayPlus.Transaction.buildOptions(options);
            final URL endpoint = new URL(String.format("%s/%s", WebpayPlus.getCurrentIntegrationTypeUrl(options.getIntegrationType()), token));

            try {
                return WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.GET, options, WebpayPlusTransactionStatusResponse.class);
            } catch (TransbankException e) {
                throw new TransactionStatusException(e);
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

        private static Options buildOptions(Options options) {
            // set default options for Webpay Plus Normal if options are not configured yet
            if (Options.isEmpty(options) && Options.isEmpty(WebpayPlus.DeferredTransaction.getOptions()))
                return WebpayPlus.DeferredTransaction.buildOptionsForTesting();

            return WebpayPlus.DeferredTransaction.getOptions().buildOptions(options);
        }

        public static WebpayPlusTransactionCreateResponse create(
                String buyOrder, String sessionId, double amount, String returnUrl) throws IOException, TransactionCreateException {
            return WebpayPlus.DeferredTransaction.create(buyOrder, sessionId, amount, returnUrl, null);
        }

        public static WebpayPlusTransactionCreateResponse create(
                String buyOrder, String sessionId, double amount, String returnUrl, Options options) throws IOException, TransactionCreateException {
            options = WebpayPlus.DeferredTransaction.buildOptions(options);
            return WebpayPlus.Transaction.create(buyOrder, sessionId, amount, returnUrl, options);
        }

        public static WebpayPlusTransactionCommitResponse commit(String token) throws IOException, TransactionCommitException {
            return WebpayPlus.DeferredTransaction.commit(token, null);
        }

        public static WebpayPlusTransactionCommitResponse commit(String token, Options options)
                throws IOException, TransactionCommitException {
            options = WebpayPlus.DeferredTransaction.buildOptions(options);
            return WebpayPlus.Transaction.commit(token, options);
        }

        public static WebpayPlusTransactionRefundResponse refund(String token, double amount)
                throws IOException, TransactionRefundException {
            return WebpayPlus.DeferredTransaction.refund(token, amount, null);
        }

        public static WebpayPlusTransactionRefundResponse refund(String token, double amount, Options options)
                throws IOException, TransactionRefundException {
            options = WebpayPlus.DeferredTransaction.buildOptions(options);
            return WebpayPlus.Transaction.refund(token, amount, options);
        }

        public static WebpayPlusTransactionStatusResponse status(String token) throws IOException, TransactionStatusException {
            return WebpayPlus.DeferredTransaction.status(token, null);
        }

        public static WebpayPlusTransactionStatusResponse status(String token, Options options)
                throws IOException, TransactionStatusException {
            options = WebpayPlus.DeferredTransaction.buildOptions(options);
            return WebpayPlus.Transaction.status(token, options);
        }

        public static WebpayPlusTransactionCaptureResponse capture(
                String token, String buyOrder, String authorizationCode, double amount) throws IOException, TransactionCaptureException {
            return WebpayPlus.DeferredTransaction.capture(token, buyOrder, authorizationCode, amount, null);
        }

        public static WebpayPlusTransactionCaptureResponse capture(
                String token, String buyOrder, String authorizationCode, double amount, Options options)
                throws IOException, TransactionCaptureException {
            options = WebpayPlus.DeferredTransaction.buildOptions(options);
            return WebpayPlus.capture(token, null, buyOrder, authorizationCode, amount, WebpayPlusTransactionCaptureResponse.class, options);
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

        private static Options buildOptions(Options options) {
            // set default options for Webpay Plus Normal if options are not configured yet
            if (Options.isEmpty(options) && Options.isEmpty(WebpayPlus.MallTransaction.getOptions()))
                return WebpayPlus.MallTransaction.buildOptionsForTesting();

            return WebpayPlus.MallTransaction.getOptions().buildOptions(options);
        }

        public static WebpayPlusMallTransactionCreateResponse create (
                String buyOrder, String sessionId, String returnUrl, MallTransactionCreateDetails details) throws IOException, TransactionCreateException {
            return MallTransaction.create (buyOrder, sessionId, returnUrl, details, null);
        }

        public static WebpayPlusMallTransactionCreateResponse create (
                String buyOrder, String sessionId, String returnUrl, MallTransactionCreateDetails details, Options options) throws IOException, TransactionCreateException {
            options = WebpayPlus.MallTransaction.buildOptions(options);
            final URL endpoint = new URL(getCurrentIntegrationTypeUrl(options.getIntegrationType()));
            WebpayApiRequest request = new MallTransactionCreateRequest(buyOrder, sessionId, returnUrl, details.getDetails());

            try {
                return WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.POST, request, options, WebpayPlusMallTransactionCreateResponse.class);
            } catch (TransbankException e) {
                throw new TransactionCreateException(e);
            }
        }

        public static WebpayPlusMallTransactionCommitResponse commit(String token) throws IOException, TransactionCommitException {
            return WebpayPlus.MallTransaction.commit(token, null);
        }

        public static WebpayPlusMallTransactionCommitResponse commit(String token, Options options)
                throws IOException, TransactionCommitException {
            options = WebpayPlus.MallTransaction.buildOptions(options);
            final URL endpoint = new URL(String.format("%s/%s", WebpayPlus.getCurrentIntegrationTypeUrl(options.getIntegrationType()), token));

            try {
                return WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.PUT, options, WebpayPlusMallTransactionCommitResponse.class);
            } catch (TransbankException e) {
                throw new TransactionCommitException(e);
            }
        }

        public static WebpayPlusMallTransactionRefundResponse refund(String token, String buyOrder, String commerceCode, double amount)
                throws IOException, TransactionRefundException {
            return WebpayPlus.MallTransaction.refund(token, buyOrder, commerceCode, amount, null);
        }

        public static WebpayPlusMallTransactionRefundResponse refund(String token, String buyOrder, String commerceCode, double amount, Options options)
                throws IOException, TransactionRefundException {
            options = WebpayPlus.MallTransaction.buildOptions(options);
            final URL endpoint = new URL(String.format("%s/%s/refunds", WebpayPlus.getCurrentIntegrationTypeUrl(options.getIntegrationType()), token));
            final WebpayApiRequest request = new TransactionRefundRequest(buyOrder, commerceCode, amount);

            try {
                return WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.POST, request, options, WebpayPlusMallTransactionRefundResponse.class);
            } catch (TransbankException e) {
                throw new TransactionRefundException(e);
            }
        }

        public static WebpayPlusMallTransactionStatusResponse status(String token) throws IOException, TransactionStatusException {
            return WebpayPlus.MallTransaction.status(token, null);
        }

        public static WebpayPlusMallTransactionStatusResponse status(String token, Options options)
                throws IOException, TransactionStatusException {
            options = WebpayPlus.MallTransaction.buildOptions(options);
            final URL endpoint = new URL(String.format("%s/%s", WebpayPlus.getCurrentIntegrationTypeUrl(options.getIntegrationType()), token));

            try {
                return WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.GET, options, WebpayPlusMallTransactionStatusResponse.class);
            } catch (TransbankException e) {
                throw new TransactionStatusException(e);
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

        private static Options buildOptions(Options options) {
            // set default options for Webpay Plus Normal if options are not configured yet
            if (Options.isEmpty(options) && Options.isEmpty(WebpayPlus.MallDeferredTransaction.getOptions()))
                return WebpayPlus.MallDeferredTransaction.buildOptionsForTesting();

            return WebpayPlus.MallDeferredTransaction.getOptions().buildOptions(options);
        }

        public static WebpayPlusMallTransactionCreateResponse create (
                String buyOrder, String sessionId, String returnUrl, MallTransactionCreateDetails details) throws IOException, TransactionCreateException {
            return MallDeferredTransaction.create (buyOrder, sessionId, returnUrl, details, null);
        }

        public static WebpayPlusMallTransactionCreateResponse create (
                String buyOrder, String sessionId, String returnUrl, MallTransactionCreateDetails details, Options options) throws IOException, TransactionCreateException {
            options = WebpayPlus.MallDeferredTransaction.buildOptions(options);
            return WebpayPlus.MallTransaction.create (buyOrder, sessionId, returnUrl, details, options);
        }

        public static WebpayPlusMallTransactionCommitResponse commit(String token) throws IOException, TransactionCommitException {
            return WebpayPlus.MallDeferredTransaction.commit(token, null);
        }

        public static WebpayPlusMallTransactionCommitResponse commit(String token, Options options)
                throws IOException, TransactionCommitException {
            options = WebpayPlus.MallDeferredTransaction.buildOptions(options);
            return WebpayPlus.MallTransaction.commit(token, options);
        }

        public static WebpayPlusMallTransactionRefundResponse refund(String token, String buyOrder, String commerceCode, double amount)
                throws IOException, TransactionRefundException {
            return WebpayPlus.MallDeferredTransaction.refund(token, buyOrder, commerceCode, amount, null);
        }

        public static WebpayPlusMallTransactionRefundResponse refund(String token, String buyOrder, String commerceCode, double amount, Options options)
                throws IOException, TransactionRefundException {
            options = WebpayPlus.MallDeferredTransaction.buildOptions(options);
            return WebpayPlus.MallTransaction.refund(token, buyOrder, commerceCode, amount, options);
        }

        public static WebpayPlusMallTransactionStatusResponse status(String token) throws IOException, TransactionStatusException {
            return WebpayPlus.MallDeferredTransaction.status(token, null);
        }

        public static WebpayPlusMallTransactionStatusResponse status(String token, Options options)
                throws IOException, TransactionStatusException {
            options = WebpayPlus.MallDeferredTransaction.buildOptions(options);
            return WebpayPlus.MallTransaction.status(token, options);
        }

        public static WebpayPlusMallTransactionCaptureResponse capture(
                String token, String commerceCode, String buyOrder, String authorizationCode, double amount) throws IOException, TransactionCaptureException {
            return WebpayPlus.MallDeferredTransaction.capture(token, commerceCode, buyOrder, authorizationCode, amount, null);
        }

        public static WebpayPlusMallTransactionCaptureResponse capture(
                String token, String commerceCode, String buyOrder, String authorizationCode, double amount, Options options)
                throws IOException, TransactionCaptureException {
            options = WebpayPlus.MallDeferredTransaction.buildOptions(options);
            return WebpayPlus.capture(token, commerceCode, buyOrder, authorizationCode, amount, WebpayPlusMallTransactionCaptureResponse.class, options);
        }
    }
}

