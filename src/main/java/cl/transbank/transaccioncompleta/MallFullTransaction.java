package cl.transbank.transaccioncompleta;

import cl.transbank.exception.TransbankException;
import cl.transbank.model.MallTransactionCreateDetails;
import cl.transbank.model.WebpayApiRequest;
import cl.transbank.transaccioncompleta.model.*;
import cl.transbank.util.HttpUtil;
import cl.transbank.webpay.IntegrationType;
import cl.transbank.webpay.Options;
import cl.transbank.webpay.WebpayApiResource;
import cl.transbank.webpay.exception.TransactionCommitException;
import cl.transbank.webpay.exception.TransactionCreateException;
import cl.transbank.webpay.exception.TransactionInstallmentException;
import cl.transbank.webpay.exception.TransactionStatusException;
import cl.transbank.webpay.webpayplus.WebpayPlus;
import lombok.AccessLevel;
import lombok.Getter;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Logger;

public class MallFullTransaction {
    private static Logger logger = Logger.getLogger(MallFullTransaction.class.getName());

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
        private static final String installmentURL = "installments";

        public static void setCommerceCode(String commerceCode) {
            MallFullTransaction.Transaction.getOptions().setCommerceCode(commerceCode);
        }

        public static String getCommerceCode() {
            return MallFullTransaction.Transaction.getOptions().getCommerceCode();
        }

        public static void setApiKey(String apiKey) {
            MallFullTransaction.Transaction.getOptions().setApiKey(apiKey);
        }

        public static String getApiKey() {
            return MallFullTransaction.Transaction.getOptions().getApiKey();
        }

        public static void setIntegrationType(IntegrationType integrationType) {
            MallFullTransaction.Transaction.getOptions().setIntegrationType(integrationType);
        }

        public static IntegrationType getIntegrationType() {
            return MallFullTransaction.Transaction.getOptions().getIntegrationType();
        }

        public static Options buildOptionsForTesting() {
            return new Options(
                    "597055555551", "579B532A7440BB0C9079DED94D31EA1615BACEB56610332264630D42D0A36B1C", IntegrationType.TEST);
        }

        private static Options buildOptions(Options options) {
            // set default options for Webpay Plus Normal if options are not configured yet
            if (Options.isEmpty(options) && Options.isEmpty(MallFullTransaction.Transaction.getOptions()))
                return MallFullTransaction.Transaction.buildOptionsForTesting();

            return MallFullTransaction.Transaction.getOptions().buildOptions(options);
        }

        public static MallFullTransactionCreateResponse create(
                String buyOrder, String sessionId, String cardNumber, String cardExpirationDate, MallTransactionCreateDetails details) throws IOException, TransactionCreateException {
            return MallFullTransaction.Transaction.create(buyOrder, sessionId, cardNumber, cardExpirationDate, details, null);
        }

        public static MallFullTransactionCreateResponse create(
                String buyOrder, String sessionId, String cardNumber, String cardExpirationDate, MallTransactionCreateDetails details, Options options) throws IOException, TransactionCreateException {
            options = MallFullTransaction.Transaction.buildOptions(options);
            final URL endpoint = new URL(getCurrentIntegrationTypeUrl(options.getIntegrationType()));
            final WebpayApiRequest request = new MallFullTransactionCreateRequest(buyOrder, sessionId, cardNumber, cardExpirationDate, details.getDetails());

            try {
                return WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.POST, request, options, MallFullTransactionCreateResponse.class);
            } catch (TransbankException e) {
                throw new TransactionCreateException(e);
            }
        }

        public static MallFullTransactionInstallmentResponse installment(String token, String commerceCode, String buyOrder,byte installmentsNumber) throws IOException, TransactionInstallmentException {
            return MallFullTransaction.Transaction.installment(token, commerceCode, buyOrder, installmentsNumber, null);
        }

        public static MallFullTransactionInstallmentResponse installment(String token, String commerceCode, String buyOrder,byte installmentsNumber, Options options) throws IOException, TransactionInstallmentException {
            options = MallFullTransaction.Transaction.buildOptions(options);
            final URL endpoint = new URL(String.format("%s/%s/%s", WebpayPlus.getCurrentIntegrationTypeUrl(options.getIntegrationType()), token, installmentURL));
            final WebpayApiRequest request = new MallFullTransactionInstallmentRequest(commerceCode, buyOrder, installmentsNumber);

            try {
                return WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.POST, request, options, MallFullTransactionInstallmentResponse.class);
            } catch (TransbankException e) {
                throw new TransactionInstallmentException(e);
            }
        }

        public static MallFullTransactionCommitResponse commit(String token, MallTransactionCommitDetails details) throws IOException, TransactionCommitException {
            return MallFullTransaction.Transaction.commit(token, details, null);
        }

        public static MallFullTransactionCommitResponse commit(String token, MallTransactionCommitDetails details, Options options) throws IOException, TransactionCommitException {
            options = MallFullTransaction.Transaction.buildOptions(options);
            final URL endpoint = new URL(String.format("%s/%s", getCurrentIntegrationTypeUrl(options.getIntegrationType()), token));
            final WebpayApiRequest request = new MallFullTransactionCommitRequest(details.getDetails());

            try {
                return WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.PUT, request, options, MallFullTransactionCommitResponse.class);
            } catch (TransbankException e) {
                throw new TransactionCommitException(e);
            }
        }

        public static MallFullTransactionStatusResponse status(String token) throws IOException, TransactionStatusException {
            return MallFullTransaction.Transaction.status(token, null);
        }

        public static MallFullTransactionStatusResponse status(String token, Options options)
                throws IOException, TransactionStatusException {
            options = MallFullTransaction.Transaction.buildOptions(options);
            final URL endpoint = new URL(String.format("%s/%s", getCurrentIntegrationTypeUrl(options.getIntegrationType()), token));

            try {
                return WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.GET, options, MallFullTransactionStatusResponse.class);
            } catch (TransbankException e) {
                throw new TransactionStatusException(e);
            }
        }
    }
}
