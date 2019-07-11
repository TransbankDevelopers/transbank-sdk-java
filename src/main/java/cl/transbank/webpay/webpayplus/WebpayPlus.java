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

    @Getter(AccessLevel.PRIVATE)
    private static Options options = new Options();

    private static Options setOptions(Options options) {
        WebpayPlus.options = options;
        return  options;
    }

    public static String getCurrentIntegrationTypeUrl(IntegrationType integrationType) {
        if (null == integrationType)
            integrationType = IntegrationType.TEST;

        return String.format(
                "%s/rswebpaytransaction/api/webpay/v1.0/transactions",
                integrationType.getApiBase());

    }

    public static void setCommerceCode(String commerceCode) {
        WebpayPlus.getOptions().setCommerceCode(commerceCode);
    }

    public static String getCommerceCode() {
        return WebpayPlus.getOptions().getCommerceCode();
    }

    public static void setApiKey(String apiKey) {
        WebpayPlus.getOptions().setApiKey(apiKey);
    }

    public static String getApiKey() {
        return WebpayPlus.getOptions().getApiKey();
    }

    public static void setIntegrationType(IntegrationType integrationType) {
        WebpayPlus.getOptions().setIntegrationType(integrationType);
    }

    public static IntegrationType getIntegrationType() {
        return WebpayPlus.getOptions().getIntegrationType();
    }

    public static Options buildOptionsForTestingWebpayPlusNormal() {
        return new Options(
                "597055555532", "579B532A7440BB0C9079DED94D31EA1615BACEB56610332264630D42D0A36B1C", IntegrationType.TEST);
    }

    public static Options buildOptionsForTestingWebpayPlusMall() {
        return new Options(
                "597055555535", "579B532A7440BB0C9079DED94D31EA1615BACEB56610332264630D42D0A36B1C", IntegrationType.TEST);
    }

    public static Options buildOptionsForTestingWebpayPlusMallDeferred() {
        return new Options(
                "597055555544", "579B532A7440BB0C9079DED94D31EA1615BACEB56610332264630D42D0A36B1C", IntegrationType.TEST);
    }

    public static Options buildOptionsForTestingWebpayPlusDeferredCapture() {
        return new Options(
                "597055555540", "579B532A7440BB0C9079DED94D31EA1615BACEB56610332264630D42D0A36B1C", IntegrationType.TEST);
    }

    public static Options configureNormalForTesting() {
        return WebpayPlus.setOptions(buildOptionsForTestingWebpayPlusNormal());
    }

    public static Options configureMallForTesting() {
        return WebpayPlus.setOptions(buildOptionsForTestingWebpayPlusMall());
    }

    public static Options configureDeferredForTesting() {
        return WebpayPlus.setOptions(buildOptionsForTestingWebpayPlusDeferredCapture());
    }

    private static Options buildNormalOptions(Options options) {
        // set default options for Webpay Plus Normal if options are not configured yet
        if (Options.isEmpty(options) && Options.isEmpty(WebpayPlus.getOptions()))
            return buildOptionsForTestingWebpayPlusNormal();

        return WebpayPlus.getOptions().buildOptions(options);
    }

    private static Options buildMallOptions(Options options) {
        // set default options for Webpay Plus Mall if options are not configured yet
        if (Options.isEmpty(options) && Options.isEmpty(WebpayPlus.getOptions()))
            return buildOptionsForTestingWebpayPlusMall();

        return WebpayPlus.getOptions().buildOptions(options);
    }

    private static Options buildDeferredOptions(Options options) {
        // set default options for Webpay Plus Deferred Capture if options are not configured yet
        if (WebpayPlus.getOptions().isEmpty() && Options.isEmpty(options))
            return buildOptionsForTestingWebpayPlusDeferredCapture();

        return WebpayPlus.getOptions().buildOptions(options);
    }

    private static Options buildMallDeferredOptions(Options options) {
        // set default options for Webpay Plus Mall Deferred if options are not configured yet
        if (Options.isEmpty(options) && Options.isEmpty(WebpayPlus.getOptions()))
            return buildOptionsForTestingWebpayPlusMallDeferred();

        return WebpayPlus.getOptions().buildOptions(options);
    }

    private static CaptureWebpayPlusTransactionResponse capture(
            String token, String commerceCode, String buyOrder, String authorizationCode, double amount, Options options)
            throws CaptureTransactionException {
        try {
            final URL endpoint = new URL(String.format("%s/%s/capture", WebpayPlus.getCurrentIntegrationTypeUrl(options.getIntegrationType()), token));
            final WebpayApiRequest request = new TransactionCaptureRequest(commerceCode, buyOrder, authorizationCode, amount);
            return WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.PUT, request, options, CaptureWebpayPlusTransactionResponse.class);
        } catch (Exception e) {
            throw new CaptureTransactionException(e);
        }
    }

    public static class Transaction {
        public static CreateWebpayPlusTransactionResponse create(
                String buyOrder, String sessionId, double amount, String returnUrl) throws CreateTransactionException {
            return WebpayPlus.Transaction.create(buyOrder, sessionId, amount, returnUrl, null);
        }

        public static CreateWebpayPlusTransactionResponse create(
                String buyOrder, String sessionId, double amount, String returnUrl, Options options) throws CreateTransactionException {
            try {
                options = WebpayPlus.buildNormalOptions(options);
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
                options = WebpayPlus.buildNormalOptions(options);
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
                options = WebpayPlus.buildNormalOptions(options);
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
                options = WebpayPlus.buildNormalOptions(options);
                final URL endpoint = new URL(String.format("%s/%s", WebpayPlus.getCurrentIntegrationTypeUrl(options.getIntegrationType()), token));
                return WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.GET, options, StatusWebpayPlusTransactionResponse.class);
            } catch (Exception e) {
                throw new StatusTransactionException(e);
            }
        }

        /*
         * Webpay Plus Mall Deferred Capture
         */
        public static CaptureWebpayPlusTransactionResponse capture(
                String token, String commerceCode, String buyOrder, String authorizationCode, double amount) throws CaptureTransactionException {
            return capture(token, commerceCode, buyOrder, authorizationCode, amount, null);
        }

        /*
         * Webpay Plus Mall Deferred Capture
         */
        public static CaptureWebpayPlusTransactionResponse capture(
                String token, String commerceCode, String buyOrder, String authorizationCode, double amount, Options options)
                throws CaptureTransactionException {
            return WebpayPlus.capture(token, commerceCode, buyOrder, authorizationCode, amount, options);
        }
    }

    public static class DeferredTransaction {
        public static CreateWebpayPlusTransactionResponse create(
                String buyOrder, String sessionId, double amount, String returnUrl) throws CreateTransactionException {
            return WebpayPlus.DeferredTransaction.create(buyOrder, sessionId, amount, returnUrl, null);
        }

        public static CreateWebpayPlusTransactionResponse create(
                String buyOrder, String sessionId, double amount, String returnUrl, Options options) throws CreateTransactionException {
            options = WebpayPlus.buildDeferredOptions(options);
            return WebpayPlus.Transaction.create(buyOrder, sessionId, amount, returnUrl, options);
        }

        public static CommitWebpayPlusTransactionResponse commit(String token) throws CommitTransactionException {
            return WebpayPlus.DeferredTransaction.commit(token, null);
        }

        public static CommitWebpayPlusTransactionResponse commit(String token, Options options)
                throws CommitTransactionException {
            options = WebpayPlus.buildDeferredOptions(options);
            return WebpayPlus.Transaction.commit(token, options);
        }

        public static RefundWebpayPlusTransactionResponse refund(String token, double amount)
                throws RefundTransactionException {
            return WebpayPlus.DeferredTransaction.refund(token, amount, null);
        }

        public static RefundWebpayPlusTransactionResponse refund(String token, double amount, Options options)
                throws RefundTransactionException {
            options = WebpayPlus.buildDeferredOptions(options);
            return WebpayPlus.Transaction.refund(token, amount, options);
        }

        public static StatusWebpayPlusTransactionResponse status(String token) throws StatusTransactionException {
            return WebpayPlus.DeferredTransaction.status(token, null);
        }

        public static StatusWebpayPlusTransactionResponse status(String token, Options options)
                throws StatusTransactionException {
            options = WebpayPlus.buildDeferredOptions(options);
            return WebpayPlus.Transaction.status(token, options);
        }

        public static CaptureWebpayPlusTransactionResponse capture(
                String token, String buyOrder, String authorizationCode, double amount) throws CaptureTransactionException {
            return WebpayPlus.DeferredTransaction.capture(token, buyOrder, authorizationCode, amount, null);
        }

        public static CaptureWebpayPlusTransactionResponse capture(
                String token, String buyOrder, String authorizationCode, double amount, Options options)
                throws CaptureTransactionException {
            options = WebpayPlus.buildDeferredOptions(options);
            return WebpayPlus.capture(token, null, buyOrder, authorizationCode, amount, options);
        }
    }

    public static class MallTransaction {
        public static CreateWebpayPlusMallTransactionResponse create (
                String buyOrder, String sessionId, String returnUrl, CreateMallTransactionDetails details) throws CreateTransactionException {
            return MallTransaction.create (buyOrder, sessionId, returnUrl, details, null);
        }

        public static CreateWebpayPlusMallTransactionResponse create (
                String buyOrder, String sessionId, String returnUrl, CreateMallTransactionDetails details, Options options) throws CreateTransactionException {
            try {
                options = WebpayPlus.buildMallOptions(options);
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
                options = WebpayPlus.buildMallOptions(options);
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
                options = WebpayPlus.buildMallOptions(options);
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
                options = WebpayPlus.buildMallOptions(options);
                final URL endpoint = new URL(String.format("%s/%s", WebpayPlus.getCurrentIntegrationTypeUrl(options.getIntegrationType()), token));
                return WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.GET, options, StatusWebpayPlusMallTransactionResponse.class);
            } catch (Exception e) {
                throw new StatusTransactionException(e);
            }
        }
    }

    public static class MallDeferredTransaction {
        public static CreateWebpayPlusMallTransactionResponse create (
                String buyOrder, String sessionId, String returnUrl, CreateMallTransactionDetails details) throws CreateTransactionException {
            return MallDeferredTransaction.create (buyOrder, sessionId, returnUrl, details, null);
        }

        public static CreateWebpayPlusMallTransactionResponse create (
                String buyOrder, String sessionId, String returnUrl, CreateMallTransactionDetails details, Options options) throws CreateTransactionException {
            options = WebpayPlus.buildMallDeferredOptions(options);
            return WebpayPlus.MallTransaction.create (buyOrder, sessionId, returnUrl, details, options);
        }

        public static CommitWebpayPlusMallTransactionResponse commit(String token) throws CommitTransactionException {
            return WebpayPlus.MallDeferredTransaction.commit(token, null);
        }

        public static CommitWebpayPlusMallTransactionResponse commit(String token, Options options)
                throws CommitTransactionException {
            options = WebpayPlus.buildMallDeferredOptions(options);
            return WebpayPlus.MallTransaction.commit(token, options);
        }

        public static RefundWebpayPlusMallTransactionResponse refund(String token, String buyOrder, String commerceCode, double amount)
                throws RefundTransactionException {
            return WebpayPlus.MallDeferredTransaction.refund(token, buyOrder, commerceCode, amount, null);
        }

        public static RefundWebpayPlusMallTransactionResponse refund(String token, String buyOrder, String commerceCode, double amount, Options options)
                throws RefundTransactionException {
            options = WebpayPlus.buildMallDeferredOptions(options);
            return WebpayPlus.MallTransaction.refund(token, buyOrder, commerceCode, amount, options);
        }
    }
}