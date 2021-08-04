package cl.transbank.transaccioncompleta;

import cl.transbank.common.IntegrationType;
import cl.transbank.common.IntegrationTypeHelper;
import cl.transbank.common.Options;
import cl.transbank.exception.TransbankException;
import cl.transbank.model.MallTransactionCreateDetails;
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

public class MallFullTransaction {
    private static Logger logger = Logger.getLogger(MallFullTransaction.class.getName());

    public static String getCurrentIntegrationTypeUrl(IntegrationType integrationType) {
        if (null == integrationType)
            integrationType = IntegrationType.TEST;

        return String.format(
                "%s/rswebpaytransaction/api/webpay/v1.2/transactions",
                IntegrationTypeHelper.getWebpayIntegrationType(integrationType));

    }

    public static class Transaction {
        @Getter(AccessLevel.PRIVATE)
        private static Options options = new WebpayOptions();
        private static final String installmentURL = "installments";
        private static final String refundURL = "refunds";
        private static final String captureURL = "capture";

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
            return new WebpayOptions(
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

        @Deprecated
        public static MallFullTransactionInstallmentsResponse installment(String token, MallFullTransactionInstallmentsDetails details) throws IOException, TransactionInstallmentException {
            return installments(token, details);
        }

        @Deprecated
        public static MallFullTransactionInstallmentsResponse installment(String token, MallFullTransactionInstallmentsDetails details, Options options) throws IOException, TransactionInstallmentException {
            return installments(token, details, options);
        }

        public static MallFullTransactionInstallmentsResponse installments(String token, MallFullTransactionInstallmentsDetails details) throws IOException, TransactionInstallmentException {
            return MallFullTransaction.Transaction.installments(token, details, null);
        }

        public static MallFullTransactionInstallmentsResponse installments(String token, MallFullTransactionInstallmentsDetails details, Options options) throws IOException, TransactionInstallmentException {
            options = MallFullTransaction.Transaction.buildOptions(options);
            final URL endpoint = new URL(String.format("%s/%s/%s", MallFullTransaction.getCurrentIntegrationTypeUrl(options.getIntegrationType()), token, installmentURL));
            MallFullTransactionInstallmentsResponse response =  MallFullTransactionInstallmentsResponse.build();

            for(MallFullTransactionInstallmentsDetails.Detail detail: details.getDetails()) {
                final WebpayApiRequest request = new MallFullTransactionInstallmentsRequest(detail.getCommerceCode(), detail.getBuyOrder(), detail.getInstallmentsNumber());

                try {
                    response.add(WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.POST, request, options, MallFullTransactionInstallmentResponse.class));
                } catch (TransbankException e) {
                    throw new TransactionInstallmentException(e);
                }
            }
            return response;
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

        public static MallFullTransactionRefundResponse refund(String token, double amount,String commerceCode, String buyOrder) throws IOException, TransactionRefundException {
            return MallFullTransaction.Transaction.refund(token, amount,commerceCode,buyOrder,null);
        }

        public static MallFullTransactionRefundResponse refund(String token, double amount, String commerceCode, String buyOrder, Options options)
                throws IOException, TransactionRefundException {

            options = MallFullTransaction.Transaction.buildOptions(options);
            final URL endpoint = new URL(String.format("%s/%s/%s", MallFullTransaction.getCurrentIntegrationTypeUrl(options.getIntegrationType()), token, refundURL));
            final WebpayApiRequest request = new MallFullTransactionRefundRequest(amount,commerceCode,buyOrder);

            try {
                return WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.POST, request, options, MallFullTransactionRefundResponse.class);
            } catch (TransbankException e) {
                throw new TransactionRefundException(e);
            }
        }

        public static MallFullTransactionCaptureResponse capture(String token, String commerceCode, String buyOrder, String authorizationCode, double captureAmount) throws IOException, TransactionCaptureException {
            return MallFullTransaction.Transaction.capture(token, commerceCode, buyOrder, authorizationCode, captureAmount, null);
        }

        public static MallFullTransactionCaptureResponse capture(String token, String commerceCode, String buyOrder, String authorizationCode, double captureAmount, Options options)
                throws IOException, TransactionCaptureException {

            options = MallFullTransaction.Transaction.buildOptions(options);
            final URL endpoint = new URL(String.format("%s/%s/%s", MallFullTransaction.getCurrentIntegrationTypeUrl(options.getIntegrationType()), token, captureURL));
            final WebpayApiRequest request = new MallFullTransactionCaptureRequest(commerceCode, buyOrder, authorizationCode, captureAmount);

            try {
                return WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.PUT, request, options, MallFullTransactionCaptureResponse.class);
            } catch (TransbankException e) {
                throw new TransactionCaptureException(e);
            }
        }
    }
}
