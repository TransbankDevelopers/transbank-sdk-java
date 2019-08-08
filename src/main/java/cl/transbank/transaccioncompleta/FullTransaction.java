package cl.transbank.transaccioncompleta;

import cl.transbank.exception.TransbankException;
import cl.transbank.model.WebpayApiRequest;
import cl.transbank.transaccioncompleta.model.FullTransactionCreateResponse;
import cl.transbank.transaccioncompleta.model.FullTransactionInstallmentResponse;
import cl.transbank.util.HttpUtil;
import cl.transbank.webpay.IntegrationType;
import cl.transbank.webpay.Options;
import cl.transbank.webpay.WebpayApiResource;
import cl.transbank.webpay.exception.TransactionCreateException;
import cl.transbank.webpay.exception.TransactionInstallmentException;
import cl.transbank.webpay.webpayplus.WebpayPlus;
import lombok.AccessLevel;
import lombok.Getter;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FullTransaction {
    private static Logger logger = Logger.getLogger(FullTransaction.class.getName());

    public static String getCurrentIntegrationTypeUrl(IntegrationType integrationType) {
        if (null == integrationType)
            integrationType = IntegrationType.TEST;

        return String.format(
                "%s/rswebpaytransaction/api/webpay/v1.0/transactions",
                integrationType.getApiBase());

    }

    public static class Transaction {
        @Getter(AccessLevel.PRIVATE) private static Options options = new Options();
        private static final String installmentURL = "/installments";

        public static void setCommerceCode(String commerceCode) {
            FullTransaction.Transaction.getOptions().setCommerceCode(commerceCode);
        }

        public static String getCommerceCode() {
            return FullTransaction.Transaction.getOptions().getCommerceCode();
        }

        public static void setApiKey(String apiKey) {
            FullTransaction.Transaction.getOptions().setApiKey(apiKey);
        }

        public static String getApiKey() {
            return FullTransaction.Transaction.getOptions().getApiKey();
        }

        public static void setIntegrationType(IntegrationType integrationType) {
            FullTransaction.Transaction.getOptions().setIntegrationType(integrationType);
        }

        public static IntegrationType getIntegrationType() {
            return FullTransaction.Transaction.getOptions().getIntegrationType();
        }

        public static Options buildOptionsForTesting() {
            return new Options(
                    "597055555530", "579B532A7440BB0C9079DED94D31EA1615BACEB56610332264630D42D0A36B1C", IntegrationType.TEST);
        }

        private static Options buildOptions(Options options) {
            // set default options for Webpay Plus Normal if options are not configured yet
            if (Options.isEmpty(options) && Options.isEmpty(FullTransaction.Transaction.getOptions()))
                return FullTransaction.Transaction.buildOptionsForTesting();

            return FullTransaction.Transaction.getOptions().buildOptions(options);
        }

        public static FullTransactionCreateResponse create(
                String buyOrder, String sessionId, double amount, String cardNumber, String cardExpirationDate, short cvv) throws IOException, TransactionCreateException {
            return FullTransaction.Transaction.create(buyOrder, sessionId, amount, cardNumber, cvv, cardExpirationDate,  null);
        }

        public static FullTransactionCreateResponse create(
                String buyOrder, String sessionId, double amount, String cardNumber, short cvv, String cardExpirationDate, Options options) throws IOException, TransactionCreateException {
            options = FullTransaction.Transaction.buildOptions(options);
            final URL endpoint = new URL(getCurrentIntegrationTypeUrl(options.getIntegrationType()));
            final WebpayApiRequest request = new TransactionCreateRequest(buyOrder, sessionId, amount, cardNumber, cvv, cardExpirationDate);

            try {
                return WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.POST, request, options, FullTransactionCreateResponse.class);
            } catch (TransbankException e) {
                throw new TransactionCreateException(e);
            }
        }

        public static FullTransactionInstallmentResponse installment(String token, byte installmentsNumber) throws IOException, TransactionInstallmentException {
            return FullTransaction.Transaction.installment(token, installmentsNumber, null);
        }

        public static FullTransactionInstallmentResponse installment(String token, byte installmentsNumber, Options options) throws IOException, TransactionInstallmentException {
            options = FullTransaction.Transaction.buildOptions(options);
            final URL endpoint = new URL(String.format("%s/%s/%s", WebpayPlus.getCurrentIntegrationTypeUrl(options.getIntegrationType()), token, installmentURL));
            final WebpayApiRequest request = new TransactionInstallmentRequest(installmentsNumber);
            try {
                return WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.POST, request, options, FullTransactionInstallmentResponse.class);
            } catch (TransbankException e) {
                throw new TransactionInstallmentException(e);
            }
        }
    }
}
