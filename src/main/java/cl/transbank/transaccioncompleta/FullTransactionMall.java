package cl.transbank.transaccioncompleta;

import cl.transbank.exception.TransbankException;
import cl.transbank.model.MallTransactionCreateDetails;
import cl.transbank.model.WebpayApiRequest;
import cl.transbank.transaccioncompleta.model.FullTransactionMallCreateResponse;
import cl.transbank.util.HttpUtil;
import cl.transbank.webpay.IntegrationType;
import cl.transbank.webpay.Options;
import cl.transbank.webpay.WebpayApiResource;
import cl.transbank.webpay.exception.TransactionCreateException;
import lombok.AccessLevel;
import lombok.Getter;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Logger;

public class FullTransactionMall {
    private static Logger logger = Logger.getLogger(FullTransactionMall.class.getName());

    public static String getCurrentIntegrationTypeUrl(IntegrationType integrationType) {
        if (null == integrationType)
            integrationType = IntegrationType.TEST;

        return String.format(
                "%s/rswebpaytransaction/api/webpay/v1.0/transactions",
                integrationType.getApiBase());

    }

    public static class Transaction {
        @Getter(AccessLevel.PRIVATE)
        private static Options options = new Options();

        public static void setCommerceCode(String commerceCode) {
            FullTransactionMall.Transaction.getOptions().setCommerceCode(commerceCode);
        }

        public static String getCommerceCode() {
            return FullTransactionMall.Transaction.getOptions().getCommerceCode();
        }

        public static void setApiKey(String apiKey) {
            FullTransactionMall.Transaction.getOptions().setApiKey(apiKey);
        }

        public static String getApiKey() {
            return FullTransactionMall.Transaction.getOptions().getApiKey();
        }

        public static void setIntegrationType(IntegrationType integrationType) {
            FullTransactionMall.Transaction.getOptions().setIntegrationType(integrationType);
        }

        public static IntegrationType getIntegrationType() {
            return FullTransactionMall.Transaction.getOptions().getIntegrationType();
        }

        public static Options buildOptionsForTesting() {
            return new Options(
                    "597055555530", "579B532A7440BB0C9079DED94D31EA1615BACEB56610332264630D42D0A36B1C", IntegrationType.TEST);
        }

        private static Options buildOptions(Options options) {
            // set default options for Webpay Plus Normal if options are not configured yet
            if (Options.isEmpty(options) && Options.isEmpty(FullTransactionMall.Transaction.getOptions()))
                return FullTransactionMall.Transaction.buildOptionsForTesting();

            return FullTransactionMall.Transaction.getOptions().buildOptions(options);
        }

        public static FullTransactionMallCreateResponse create(
                String buyOrder, String sessionId, double amount, String cardNumber, String cardExpirationDate, MallTransactionCreateDetails details) throws IOException, TransactionCreateException {
            return FullTransactionMall.Transaction.create(buyOrder, sessionId, cardNumber, cardExpirationDate, details, null);
        }

        public static FullTransactionMallCreateResponse create(
                String buyOrder, String sessionId, String cardNumber, String cardExpirationDate, MallTransactionCreateDetails details, Options options) throws IOException, TransactionCreateException {
            options = FullTransactionMall.Transaction.buildOptions(options);
            final URL endpoint = new URL(getCurrentIntegrationTypeUrl(options.getIntegrationType()));
            final WebpayApiRequest request = new MallTransactionCreateRequest(buyOrder, sessionId, cardNumber, cardExpirationDate, details.getDetails());

            try {
                return WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.POST, request, options, FullTransactionMallCreateResponse.class);
            } catch (TransbankException e) {
                throw new TransactionCreateException(e);
            }
        }
    }
}
