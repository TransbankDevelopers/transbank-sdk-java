package cl.transbank.webpay.webpayplus;

import cl.transbank.util.HttpUtil;
import cl.transbank.webpay.IntegrationType;
import cl.transbank.webpay.Options;
import cl.transbank.webpay.WebpayApiResource;
import cl.transbank.webpay.exception.*;
import cl.transbank.webpay.model.CardDetail;
import cl.transbank.webpay.webpayplus.model.*;
import lombok.AccessLevel;
import lombok.Getter;

import java.net.URL;
import java.util.Map;
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

    public static class Transaction {
        public static CreateWebpayPlusTransactionResponse create(
                String buyOrder, String sessionId, double amount, String returnUrl) throws CreateTransactionException {
            return WebpayPlus.Transaction.create(buyOrder, sessionId, amount, returnUrl, null);
        }

        public static CreateWebpayPlusTransactionResponse create(
                String buyOrder, String sessionId, double amount, String returnUrl, Options options)
                throws CreateTransactionException {
            try {
                options = WebpayPlus.buildNormalOptions(options);

                final URL endpoint = new URL(getCurrentIntegrationTypeUrl(options.getIntegrationType()));

                final CreateTransactionResponse out = WebpayApiResource.getHttpUtil().request(
                        endpoint,
                        HttpUtil.RequestMethod.POST,
                        new TransactionCreateRequest(buyOrder, sessionId, amount, returnUrl),
                        WebpayApiResource.buildHeaders(options),
                        CreateTransactionResponse.class);

                if (null == out)
                    throw new CreateTransactionException("Could not obtain a response from transbank webservice");

                if (null != out.getErrorMessage())
                    throw new CreateTransactionException(out.getErrorMessage());

                return new CreateWebpayPlusTransactionResponse(
                        out.getToken(),
                        out.getUrl());
            } catch (CreateTransactionException txe) {
                throw txe;
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

                final URL endpoint = new URL(
                        String.format("%s/%s", WebpayPlus.getCurrentIntegrationTypeUrl(options.getIntegrationType()), token));

                final CommitTransactionResponse out = WebpayApiResource.getHttpUtil().request(
                        endpoint,
                        HttpUtil.RequestMethod.PUT,
                        null,
                        WebpayApiResource.buildHeaders(options),
                        CommitTransactionResponse.class);

                if (null == out)
                    throw new CommitTransactionException("Could not obtain a response from transbank webservice");

                if (null != out.getErrorMessage())
                    throw new CreateTransactionException(out.getErrorMessage());

                return new CommitWebpayPlusTransactionResponse(
                        out.getVci(),
                        out.getAmount(),
                        out.getStatus(),
                        out.getBuyOrder(),
                        out.getSessionId(),
                        new CardDetail(out.getCardDetail().getCardNumber()),
                        out.getAccountingDate(),
                        out.getTransactionDate(),
                        out.getAuthorizationCode(),
                        out.getPaymentTypeCode(),
                        out.getResponseCode(),
                        out.getInstallmentsAmount(),
                        out.getInstallmentsNumber(),
                        out.getBalance());
            } catch (CommitTransactionException txe) {
                throw txe;
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

                final URL endpoint = new URL(
                        String.format("%s/%s/refunds", WebpayPlus.getCurrentIntegrationTypeUrl(options.getIntegrationType()), token));

                final RefundTransactionResponse out = WebpayApiResource.getHttpUtil().request(
                        endpoint,
                        HttpUtil.RequestMethod.POST,
                        new TransactionRefundRequest(amount),
                        WebpayApiResource.buildHeaders(options),
                        RefundTransactionResponse.class);

                if (null == out)
                    throw new RefundTransactionException("Could not obtain a response from transbank webservice");

                if (null != out.getErrorMessage())
                    throw new RefundTransactionException(out.getErrorMessage());

                return new RefundWebpayPlusTransactionResponse(
                        out.getType(),
                        out.getBalance(),
                        out.getAuthorizationCode(),
                        out.getResponseCode(),
                        out.getAuthorizationDate(),
                        out.getNullifiedAmount());
            } catch (RefundTransactionException txr) {
                throw txr;
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

                final URL endpoint = new URL(
                        String.format("%s/%s", WebpayPlus.getCurrentIntegrationTypeUrl(options.getIntegrationType()), token));

                final StatusTransactionResponse out = WebpayApiResource.getHttpUtil().request(
                        endpoint,
                        HttpUtil.RequestMethod.GET,
                        null,
                        WebpayApiResource.buildHeaders(options),
                        StatusTransactionResponse.class);

                if (null == out)
                    throw new StatusTransactionException("Could not obtain a response from transbank webservice");

                if (null != out.getErrorMessage())
                    throw new StatusTransactionException(out.getErrorMessage());

                return new StatusWebpayPlusTransactionResponse(
                        out.getVci(),
                        out.getAmount(),
                        out.getStatus(),
                        out.getBuyOrder(),
                        out.getSessionId(),
                        new CardDetail(out.getCardDetail().getCardNumber()),
                        out.getAccountingDate(),
                        out.getTransactionDate(),
                        out.getAuthorizationCode(),
                        out.getPaymentTypeCode(),
                        out.getResponseCode(),
                        out.getInstallmentsAmount(),
                        out.getInstallmentsNumber(),
                        out.getBalance());
            } catch (StatusTransactionException txs) {
                throw txs;
            } catch (Exception e) {
                throw new StatusTransactionException(e);
            }
        }

        /*
         * Webpay Plus Deferred Capture
         */
        public static CaptureWebpayPlusTransactionResponse capture(
                String token, String buyOrder, String authorizationCode, double amount) throws CaptureTransactionException {
            return capture(token, buyOrder, authorizationCode, amount, null);
        }

        /*
         * Webpay Plus Deferred Capture
         */
        public static CaptureWebpayPlusTransactionResponse capture(
                String token, String buyOrder, String authorizationCode, double amount, Options options)
                throws CaptureTransactionException {
            return _capture(token, null, buyOrder, authorizationCode, amount, options);
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
            return _capture(token, commerceCode, buyOrder, authorizationCode, amount, options);
        }

        private static CaptureWebpayPlusTransactionResponse _capture(
                String token, String commerceCode, String buyOrder, String authorizationCode, double amount, Options options)
                throws CaptureTransactionException {
            try {
                options = WebpayPlus.buildDeferredOptions(options);

                final URL endpoint = new URL(
                        String.format("%s/%s/capture", WebpayPlus.getCurrentIntegrationTypeUrl(options.getIntegrationType()), token));

                final Map<String, String> headers = WebpayApiResource.buildHeaders(options);

                final CaptureTransactionResponse out = WebpayApiResource.getHttpUtil().request(
                        endpoint,
                        HttpUtil.RequestMethod.PUT,
                        new TransactionCaptureRequest(commerceCode, buyOrder, authorizationCode, amount),
                        headers,
                        CaptureTransactionResponse.class);

                if (null == out)
                    throw new CaptureTransactionException("Could not obtain a response from transbank webservice");

                if (null != out.getErrorMessage())
                    throw new CaptureTransactionException(out.getErrorMessage());

                return new CaptureWebpayPlusTransactionResponse(
                        out.getAuthorizationCode(),
                        out.getAuthorizationDate(),
                        out.getCapturedAmount(),
                        out.getResponseCode());
            } catch (CaptureTransactionException txc) {
                throw txc;
            } catch (Exception e) {
                throw new CaptureTransactionException(e);
            }
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

                final CreateTransactionResponse out = WebpayApiResource.getHttpUtil().request(
                        endpoint,
                        HttpUtil.RequestMethod.POST,
                        new CreateMallTransactionRequest(buyOrder, sessionId, returnUrl, details.getDetails()),
                        WebpayApiResource.buildHeaders(options),
                        CreateTransactionResponse.class);

                if (null == out)
                    throw new CreateTransactionException("Could not obtain a response from transbank webservice");

                if (null != out.getErrorMessage())
                    throw new CreateTransactionException(out.getErrorMessage());

                return new CreateWebpayPlusMallTransactionResponse(
                        out.getToken(),
                        out.getUrl());
            } catch (Exception e) {
                throw new CreateTransactionException(e);
            }
        }
    }

    public static void main(String[] args) throws CreateTransactionException, CommitTransactionException, RefundTransactionException, StatusTransactionException, CaptureTransactionException {
        //Options options = new Options(null, null, IntegrationType.LIVE);
        System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$tF %1$tT] [%4$-7s] %5$s %n");
        Logger globalLog = Logger.getLogger("cl.transbank");
        globalLog.setUseParentHandlers(false);
        globalLog.addHandler(new ConsoleHandler() {
            {/*setOutputStream(System.out);*/setLevel(Level.ALL);}
        });
        globalLog.setLevel(Level.ALL);

        Options options = null;
        logger.info("---------------------------- Webpay Plus [Transaction.create] ----------------------------");
        try {
            final CreateWebpayPlusTransactionResponse create = Transaction.create("afdgef346456",
                    "432453sdfgdfgh", 1000, "http://localhost:8080", options);
            logger.info(create.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("");

        logger.info("---------------------------- Webpay Plus [Transaction.commit] ----------------------------");
        String token = "e8943e6fcc623f1336ca195a7e99c8c6a8869c1367f9205a2f5d1c329615aa51";

        try {
            final CommitWebpayPlusTransactionResponse commit = Transaction.commit(token);
            logger.info(commit.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("");

        logger.info("---------------------------- Webpay Plus [Transaction.refund] ----------------------------");
        try {
            final RefundWebpayPlusTransactionResponse refund = Transaction.refund(token, 10);
            logger.info(refund.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("");

        logger.info("---------------------------- Webpay Plus [Transaction.status] ----------------------------");
        try {
            final StatusWebpayPlusTransactionResponse status = Transaction.status(token);
            logger.info(status.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("");

        logger.info("------------------- Webpay Plus Captura Diferida [Transaction.create] -------------------");
        try {
            String buyOrder = "afdgef346456";
            final CreateWebpayPlusTransactionResponse deferredCapture = Transaction.create(buyOrder,
                    "432453sdfgdfgh", 1000, "http://localhost:8080", buildDeferredOptions(null));
            logger.info(deferredCapture.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("");

        logger.info("------------------- Webpay Plus Captura Diferida [Transaction.capture] -------------------");
        try {
            String deferredToken = "e4273b557e87d2fbb6df820dce7698d9667cb2bf08595a381f379243776d5389";
            String deferredBuyOrder = "92506929";
            String deferredAuthorizationCode = "1213";
            final CaptureWebpayPlusTransactionResponse capture = Transaction.capture(deferredToken, deferredBuyOrder, deferredAuthorizationCode, 10);
            logger.info(capture.toString());
            System.out.println("EL PICO");
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("");

        logger.info("-------------------------- Webpay Plus Mall [Transaction.create] --------------------------");
        try {
            final CreateWebpayPlusMallTransactionResponse create = MallTransaction.create("afdgef346456",
                    "432453sdfgdfgh", "http://localhost:8080", CreateMallTransactionDetails.build(
                            1000, "597055555536", "r234n347"));
            logger.info(create.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("");
    }
}