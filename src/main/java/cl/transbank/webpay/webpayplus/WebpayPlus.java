package cl.transbank.webpay.webpayplus;

import cl.transbank.util.HttpUtil;
import cl.transbank.webpay.IntegrationType;
import cl.transbank.webpay.Options;
import cl.transbank.webpay.WebpayApiResource;
import cl.transbank.webpay.exception.TransactionCommitException;
import cl.transbank.webpay.exception.TransactionCreateException;
import cl.transbank.webpay.webpayplus.model.CommitWebpayPlusTransactionResponse;
import cl.transbank.webpay.webpayplus.model.CreateWebpayPlusTransactionResponse;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
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
                    throw new TransactionCreateException("No fue posible obtener respuesta desde el servicio de transbank");

                if (null != out.getErrorMessage())
                    throw new TransactionCreateException(out.getErrorMessage());

                return new CreateWebpayPlusTransactionResponse(out.getToken(), out.getUrl());
            } catch (Exception e) {
                throw new TransactionCreateException(e);
            }
        }

        public static CommitWebpayPlusTransactionResponse commit(String token) throws TransactionCommitException {
            return null;
        }

        public static CommitWebpayPlusTransactionResponse commit(String token, Options options)
                throws TransactionCommitException {
            return null;
        }
    }

    public static void main(String[] args) throws TransactionCreateException {
        //Options options = new Options(null, null, IntegrationType.LIVE);
        Options options = null;
        final CreateWebpayPlusTransactionResponse response = Transaction.create("afdgef346456",
                "432453sdfgdfgh", 1000, "http://localhost:8080", options);
        System.out.println(response);
    }
}