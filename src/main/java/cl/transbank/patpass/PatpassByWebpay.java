package cl.transbank.patpass;

import cl.transbank.common.IntegrationType;
import cl.transbank.common.IntegrationTypeHelper;
import cl.transbank.common.Options;
import cl.transbank.exception.TransbankException;
import cl.transbank.patpass.model.PatpassByWebpayTransactionCommitResponse;
import cl.transbank.patpass.model.PatpassByWebpayTransactionRefundResponse;
import cl.transbank.patpass.model.PatpassByWebpayTransactionStatusResponse;
import cl.transbank.util.BeanUtils;
import cl.transbank.util.HttpUtil;
import cl.transbank.webpay.WebpayOptions;
import cl.transbank.webpay.WebpayApiResource;
import cl.transbank.webpay.exception.TransactionCommitException;
import cl.transbank.webpay.exception.TransactionCreateException;
import cl.transbank.patpass.model.PatpassByWebpayTransactionCreateResponse;
import cl.transbank.webpay.exception.TransactionRefundException;
import cl.transbank.webpay.exception.TransactionStatusException;
import cl.transbank.webpay.webpayplus.WebpayPlus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Logger;

public class PatpassByWebpay {
    private static Logger logger = Logger.getLogger(PatpassByWebpay.class.getName());

    @Setter(AccessLevel.PRIVATE) @Getter(AccessLevel.PRIVATE) private static Options options = new WebpayOptions();

    public static String getCurrentIntegrationTypeUrl(IntegrationType integrationType) {
        if (null == integrationType)
            integrationType = IntegrationType.TEST;

        return String.format(
                "%s/rswebpaytransaction/api/webpay/v1.2/transactions",
                IntegrationTypeHelper.getWebpayIntegrationType(integrationType));

    }

    public static void setCommerceCode(String commerceCode) {
        PatpassByWebpay.getOptions().setCommerceCode(commerceCode);
    }

    public static String getCommerceCode() {
        return PatpassByWebpay.getOptions().getCommerceCode();
    }

    public static void setApiKey(String apiKey) {
        PatpassByWebpay.getOptions().setApiKey(apiKey);
    }

    public static String getApiKey() {
        return PatpassByWebpay.getOptions().getApiKey();
    }

    public static void setIntegrationType(IntegrationType integrationType) {
        PatpassByWebpay.getOptions().setIntegrationType(integrationType);
    }

    public static IntegrationType getIntegrationType() {
        return PatpassByWebpay.getOptions().getIntegrationType();
    }

    public static Options buildOptionsForTestingPatpassByWebpay(){
        return new WebpayOptions("597055555550",
                "579B532A7440BB0C9079DED94D31EA1615BACEB56610332264630D42D0A36B1C", IntegrationType.TEST);
    }

    public static Options buildOptions(Options options) {
        // set default options for Pattpass by Webpay if options are not configured yet
        if (Options.isEmpty(options) && Options.isEmpty(PatpassByWebpay.getOptions()))
            return buildOptionsForTestingPatpassByWebpay();

        return PatpassByWebpay.getOptions().buildOptions(options);
    }

    public static class Transaction {
        public static PatpassByWebpayTransactionCreateResponse create(
                String buyOrder, String sessionId, double amount, String returnUrl, String serviceId, String cardHolderId,
                String cardHolderName, String cardHolderLastName1, String cardHolderLastName2, String cardHolderMail, String cellphoneNumber,
                String expirationDate, String commerceMail, boolean ufFlag) throws IOException, TransactionCreateException {
            return Transaction.create(buyOrder, sessionId, amount, returnUrl, serviceId, cardHolderId, cardHolderName,
                    cardHolderLastName1, cardHolderLastName2, cardHolderMail, cellphoneNumber, expirationDate, commerceMail, ufFlag, null);
        }

        public static PatpassByWebpayTransactionCreateResponse create(
                String buyOrder, String sessionId, double amount, String returnUrl, String serviceId, String cardHolderId,
                String cardHolderName, String cardHolderLastName1, String cardHolderLastName2, String cardHolderMail, String cellphoneNumber,
                String expirationDate, String commerceMail, boolean ufFlag, Options options) throws IOException, TransactionCreateException {
                options = PatpassByWebpay.buildOptions(options);
                final URL endpoint = new URL(getCurrentIntegrationTypeUrl(options.getIntegrationType()));
                final TransactionCreateRequest request = new TransactionCreateRequest(buyOrder, sessionId, amount, returnUrl);
                request.setDetails(serviceId, cardHolderId, cardHolderName, cardHolderLastName1, cardHolderLastName2,
                        cardHolderMail, cellphoneNumber, expirationDate, commerceMail, ufFlag);
            try {
                return WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.POST, request, options, PatpassByWebpayTransactionCreateResponse.class);
            } catch (TransbankException e) {
                throw new TransactionCreateException(e);
            }
        }

        public static PatpassByWebpayTransactionCommitResponse commit(String token) throws IOException, TransactionCommitException {
            return Transaction.commit(token, null);
        }

        public static PatpassByWebpayTransactionCommitResponse commit(String token, Options options) throws IOException, TransactionCommitException {
            options = PatpassByWebpay.buildOptions(options);
            try {
                return BeanUtils.getInstance().copyBeanData(new PatpassByWebpayTransactionCommitResponse(), WebpayPlus.Transaction.commit(token, options));
            } catch (TransbankException e) {
                throw new TransactionCommitException(e);
            }
        }

        public static PatpassByWebpayTransactionRefundResponse refund(String token, double amount) throws IOException, TransactionRefundException {
            return Transaction.refund(token, amount, null);
        }

        public static PatpassByWebpayTransactionRefundResponse refund(String token, double amount, Options options) throws IOException, TransactionRefundException {
            options = PatpassByWebpay.buildOptions(options);
            try {
                return BeanUtils.getInstance().copyBeanData(new PatpassByWebpayTransactionRefundResponse(), WebpayPlus.Transaction.refund(token, amount, options));
            } catch (TransbankException e) {
                throw new TransactionRefundException(e);
            }
        }

        public static PatpassByWebpayTransactionStatusResponse status(String token) throws IOException, TransactionStatusException {
            return Transaction.status(token, null);
        }

        public static PatpassByWebpayTransactionStatusResponse status(String token, Options options) throws IOException, TransactionStatusException {
            options = PatpassByWebpay.buildOptions(options);
            try {
                return BeanUtils.getInstance().copyBeanData(new PatpassByWebpayTransactionStatusResponse(), WebpayPlus.Transaction.status(token, options));
            } catch (TransbankException e) {
                throw new TransactionStatusException(e);
            }
        }
    }
}
