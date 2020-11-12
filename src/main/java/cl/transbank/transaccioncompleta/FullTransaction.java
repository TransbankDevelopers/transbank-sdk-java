package cl.transbank.transaccioncompleta;

import cl.transbank.common.IntegrationType;
import cl.transbank.common.IntegrationTypeHelper;
import cl.transbank.common.Options;
import cl.transbank.exception.TransbankException;
import cl.transbank.model.WebpayApiRequest;
import cl.transbank.transaccioncompleta.model.*;
import cl.transbank.util.HttpUtil;
import cl.transbank.webpay.WebpayOptions;
import cl.transbank.webpay.WebpayApiResource;
import cl.transbank.webpay.exception.*;
import lombok.AccessLevel;
import lombok.Getter;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Logger;

public class FullTransaction {
    private static Logger logger = Logger.getLogger(FullTransaction.class.getName());

    public static String getCurrentIntegrationTypeUrl(IntegrationType integrationType) {
        if (null == integrationType)
            integrationType = IntegrationType.TEST;

        return String.format(
                "%s/rswebpaytransaction/api/webpay/v1.0/transactions",
                IntegrationTypeHelper.getWebpayIntegrationType(integrationType));

    }

    public static class Transaction {
        @Getter(AccessLevel.PRIVATE) private static Options options = new WebpayOptions();
        private static final String installmentURL = "installments";
        private static final String refundURL = "refunds";

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
            return new WebpayOptions(
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

        @Deprecated
        public static FullTransactionInstallmentResponse installment(String token, byte installmentsNumber) throws IOException, TransactionInstallmentException {
            return installments(token,installmentsNumber);
        }

        @Deprecated
        public static FullTransactionInstallmentResponse installment(String token, byte installmentsNumber, Options options) throws IOException, TransactionInstallmentException {
           return installments(token, installmentsNumber, options);
        }

        public static FullTransactionInstallmentResponse installments(String token, byte installmentsNumber) throws IOException, TransactionInstallmentException {
            return FullTransaction.Transaction.installments(token, installmentsNumber, null);
        }

        public static FullTransactionInstallmentResponse installments(String token, byte installmentsNumber, Options options) throws  IOException, TransactionInstallmentException {
            options = FullTransaction.Transaction.buildOptions(options);
            final URL endpoint = new URL(String.format("%s/%s/%s", FullTransaction.getCurrentIntegrationTypeUrl(options.getIntegrationType()), token, installmentURL));
            final WebpayApiRequest request = new TransactionInstallmentsRequest(installmentsNumber);

            try {
                return WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.POST, request, options, FullTransactionInstallmentResponse.class);
            } catch (TransbankException e) {
                throw new TransactionInstallmentException(e);
            }
        }


        /**
         * This method didn't accept null values on fields that were optional
         *
         * @deprecated use {@link #commit(String token, Long idQueryInstallments, Byte deferredPeriodIndex, Boolean gracePeriod)} instead.
         */
        @Deprecated
        public static FullTransactionCommitResponse commit(String token, Long idQueryInstallments, byte deferredPeriodIndex, Boolean gracePeriod) throws IOException, TransactionCommitException {
            return FullTransaction.Transaction.commit(token, idQueryInstallments, deferredPeriodIndex, gracePeriod, null);
        }

        /**
         * This method didn't accept null values on fields that were optional
         *
         * @deprecated use {@link #commit(String token, Long idQueryInstallments, Byte deferredPeriodIndex, Boolean gracePeriod, Options options)} instead.
         */
        @Deprecated
        public static FullTransactionCommitResponse commit(String token, Long idQueryInstallments, byte deferredPeriodIndex, Boolean gracePeriod, Options options) throws IOException, TransactionCommitException {
            options = FullTransaction.Transaction.buildOptions(options);
            final URL endpoint = new URL(String.format("%s/%s", getCurrentIntegrationTypeUrl(options.getIntegrationType()), token));
            final WebpayApiRequest request = new TransactionCommitRequest(idQueryInstallments, deferredPeriodIndex, gracePeriod);

            try {
                return WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.PUT, request, options, FullTransactionCommitResponse.class);
            } catch (TransbankException e) {
                throw new TransactionCommitException(e);
            }
        }

        public static FullTransactionStatusResponse status(String token) throws IOException, TransactionStatusException {
            return FullTransaction.Transaction.status(token, null);
        }

        public static FullTransactionStatusResponse status(String token, Options options)
                throws IOException, TransactionStatusException {
            options = FullTransaction.Transaction.buildOptions(options);
            final URL endpoint = new URL(String.format("%s/%s", getCurrentIntegrationTypeUrl(options.getIntegrationType()), token));

            try {
                return WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.GET, options, FullTransactionStatusResponse.class);
            } catch (TransbankException e) {
                throw new TransactionStatusException(e);
            }
        }

        public static FullTransactionRefundResponse refund(String token, double amount) throws IOException, TransactionRefundException {
            return FullTransaction.Transaction.refund(token, amount,null);
        }

        public static FullTransactionRefundResponse refund(String token, double amount, Options options)
                throws IOException, TransactionRefundException {

            options = FullTransaction.Transaction.buildOptions(options);
            final URL endpoint = new URL(String.format("%s/%s/%s", FullTransaction.getCurrentIntegrationTypeUrl(options.getIntegrationType()), token, refundURL));
            final WebpayApiRequest request = new TransactionRefundRequest(amount);

            try {
                return WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.POST, request, options, FullTransactionRefundResponse.class);
            } catch (TransbankException e) {
                throw new TransactionRefundException(e);
            }
        }
    }
}
