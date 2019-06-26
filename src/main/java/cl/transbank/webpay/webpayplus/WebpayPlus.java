package cl.transbank.webpay.webpayplus;

import cl.transbank.util.HttpUtil;
import cl.transbank.webpay.IntegrationType;
import cl.transbank.webpay.Options;
import cl.transbank.webpay.WebpayApiResource;
import cl.transbank.webpay.exception.TransactionCommitException;
import cl.transbank.webpay.exception.TransactionCreateException;
import cl.transbank.webpay.model.CardDetail;
import cl.transbank.webpay.webpayplus.model.CommitWebpayPlusTransactionResponse;
import cl.transbank.webpay.webpayplus.model.CreateWebpayPlusTransactionResponse;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.net.URL;

public class WebpayPlus {
    @Setter(AccessLevel.PRIVATE) @Getter(AccessLevel.PRIVATE) private static Options options = new Options();

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

    public static void configureNormalForTesting() {
        WebpayPlus.setOptions(new Options("597055555532",
                "579B532A7440BB0C9079DED94D31EA1615BACEB56610332264630D42D0A36B1C", IntegrationType.TEST));
    }

    public static void configureMallForTesting() {
        // TODO we have not the commerce code yet
        WebpayPlus.setOptions(new Options("",
                "579B532A7440BB0C9079DED94D31EA1615BACEB56610332264630D42D0A36B1C", IntegrationType.TEST));
    }

    public static void configureDeferredForTesting() {
        // TODO we have not the commerce code yet
        WebpayPlus.setOptions(new Options("",
                "579B532A7440BB0C9079DED94D31EA1615BACEB56610332264630D42D0A36B1C", IntegrationType.TEST));
    }

    private static Options buildNormalOptions(Options options) {
        // set default options for Webpay Plus Normal if options are not configured yet
        if (WebpayPlus.getOptions().isEmpty())
            configureNormalForTesting();

        return WebpayPlus.getOptions().buildOptions(options);
    }

    public static class Transaction extends WebpayApiResource {
        public static CreateWebpayPlusTransactionResponse create(
                String buyOrder, String sessionId, double amount, String returnUrl) throws TransactionCreateException {
            return create(buyOrder, sessionId, amount, returnUrl, null);
        }

        public static CreateWebpayPlusTransactionResponse create(
                String buyOrder, String sessionId, double amount, String returnUrl, Options options)
                throws TransactionCreateException {
            try {
                options = buildNormalOptions(options);

                final URL endpoint = new URL(getCurrentIntegrationTypeUrl(options.getIntegrationType()));

                final TransactionCreateRequest in = new TransactionCreateRequest(buyOrder, sessionId, amount, returnUrl);
                final TransactionCreateResponse out = getHttpUtil().request(endpoint, HttpUtil.RequestMethod.POST, in,
                        buildHeaders(options), TransactionCreateResponse.class);

                if (null == out)
                    throw new TransactionCreateException("Could not obtain a response from transbank webservice");

                if (null != out.getErrorMessage())
                    throw new TransactionCreateException(out.getErrorMessage());

                return new CreateWebpayPlusTransactionResponse(out.getToken(), out.getUrl());
            } catch (TransactionCreateException txe) {
                throw txe;
            } catch (Exception e) {
                throw new TransactionCreateException(e);
            }
        }

        public static CommitWebpayPlusTransactionResponse commit(String token) throws TransactionCommitException {
            return commit(token, null);
        }

        public static CommitWebpayPlusTransactionResponse commit(String token, Options options)
                throws TransactionCommitException {
            try {
                options = buildNormalOptions(options);

                final URL endpoint = new URL(
                        String.format("%s/%s", getCurrentIntegrationTypeUrl(options.getIntegrationType()), token));
                final TransactionCommitResponse out = getHttpUtil().request(endpoint, HttpUtil.RequestMethod.PUT, null,
                        buildHeaders(options), TransactionCommitResponse.class);

                if (null == out)
                    throw new TransactionCommitException("Could not obtain a response from transbank webservice");

                if (null != out.getErrorMessage())
                    throw new TransactionCreateException(out.getErrorMessage());

                return new CommitWebpayPlusTransactionResponse(
                        out.getVci(), out.getAmount(), out.getStatus(), out.getBuyOrder(), out.getSessionId(),
                        new CardDetail(out.getCardDetail().getCardNumber()), out.getAccountingDate(), out.getTransactionDate(),
                        out.getAuthorizationCode(), out.getPaymentTypeCode(), out.getResponseCode(), out.getInstallmentsAmount(),
                        out.getInstallmentsNumber(), out.getBalance());
            } catch (TransactionCommitException txe) {
                throw txe;
            } catch (Exception e) {
                throw new TransactionCommitException(e);
            }
        }
    }

    public static void main(String[] args) throws TransactionCreateException, TransactionCommitException {
        //Options options = new Options(null, null, IntegrationType.LIVE);
        Options options = null;
        final CreateWebpayPlusTransactionResponse response = Transaction.create("afdgef346456",
                "432453sdfgdfgh", 1000, "http://localhost:8080", options);
        System.out.println(response);

        String token = "e3185dca43dddba6532754f80c680f092b386e376d7c23128c3413385e7a3c97";
        final CommitWebpayPlusTransactionResponse commit = Transaction.commit(token);
        System.out.println(commit);
    }
}