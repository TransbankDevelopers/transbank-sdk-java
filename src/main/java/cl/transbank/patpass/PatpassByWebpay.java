package cl.transbank.patpass;

import cl.transbank.patpass.model.CommitPatpassByWebpayTransactionResponse;
import cl.transbank.patpass.model.RefundPatpassByWebpayTransactionResponse;
import cl.transbank.patpass.model.StatusPatpassByWebpayTransactionResponse;
import cl.transbank.util.BeanUtils;
import cl.transbank.util.HttpUtil;
import cl.transbank.webpay.IntegrationType;
import cl.transbank.webpay.Options;
import cl.transbank.webpay.WebpayApiResource;
import cl.transbank.webpay.exception.CommitTransactionException;
import cl.transbank.webpay.exception.CreateTransactionException;
import cl.transbank.patpass.model.CreatePatpassByWebpayTransactionResponse;
import cl.transbank.webpay.exception.RefundTransactionException;
import cl.transbank.webpay.exception.StatusTransactionException;
import cl.transbank.webpay.webpayplus.WebpayPlus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Random;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PatpassByWebpay {
    private static Logger logger = Logger.getLogger(PatpassByWebpay.class.getName());

    @Setter(AccessLevel.PRIVATE) @Getter(AccessLevel.PRIVATE) private static Options options = new Options();

    public static String getCurrentIntegrationTypeUrl(IntegrationType integrationType) {
        if (null == integrationType)
            integrationType = IntegrationType.TEST;

        return String.format(
                "%s/rswebpaytransaction/api/webpay/v1.0/transactions",
                integrationType.getApiBase());

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
        return new Options ("597055555550",
                "579B532A7440BB0C9079DED94D31EA1615BACEB56610332264630D42D0A36B1C", IntegrationType.TEST);
    }

    public static Options buildOptions(Options options) {
        // set default options for Pattpass by Webpay if options are not configured yet
        if (Options.isEmpty(options) && Options.isEmpty(PatpassByWebpay.getOptions()))
            return buildOptionsForTestingPatpassByWebpay();

        return PatpassByWebpay.getOptions().buildOptions(options);
    }

    public static class Transaction {
        public static CreatePatpassByWebpayTransactionResponse create(
                String buyOrder, String sessionId, double amount, String returnUrl, String serviceId, String cardHolderId,
                String cardHolderName, String cardHolderLastName1, String cardHolderLastName2, String cardHolderMail, String cellphoneNumber,
                String expirationDate, String commerceMail, boolean ufFlag) throws CreateTransactionException {
            return Transaction.create(buyOrder, sessionId, amount, returnUrl, serviceId, cardHolderId, cardHolderName,
                    cardHolderLastName1, cardHolderLastName2, cardHolderMail, cellphoneNumber, expirationDate, commerceMail, ufFlag, null);
        }

        public static CreatePatpassByWebpayTransactionResponse create(
                String buyOrder, String sessionId, double amount, String returnUrl, String serviceId, String cardHolderId,
                String cardHolderName, String cardHolderLastName1, String cardHolderLastName2, String cardHolderMail, String cellphoneNumber,
                String expirationDate, String commerceMail, boolean ufFlag, Options options) throws CreateTransactionException {
            try {
                options = PatpassByWebpay.buildOptions(options);
                final URL endpoint = new URL(getCurrentIntegrationTypeUrl(options.getIntegrationType()));
                final TransactionCreateRequest request = new TransactionCreateRequest(buyOrder, sessionId, amount, returnUrl);
                request.setDetails(serviceId, cardHolderId, cardHolderName, cardHolderLastName1, cardHolderLastName2,
                        cardHolderMail, cellphoneNumber, expirationDate, commerceMail, ufFlag);
                return WebpayApiResource.execute(endpoint, HttpUtil.RequestMethod.POST, request, options, CreatePatpassByWebpayTransactionResponse.class);
            } catch (Exception e) {
                throw new CreateTransactionException(e);
            }
        }

        public static CommitPatpassByWebpayTransactionResponse commit(String token) throws CommitTransactionException {
            return Transaction.commit(token, null);
        }

        public static CommitPatpassByWebpayTransactionResponse commit(String token, Options options) throws CommitTransactionException {
            try {
                options = PatpassByWebpay.buildOptions(options);
                return BeanUtils.getInstance().copyBeanData(new CommitPatpassByWebpayTransactionResponse(), WebpayPlus.Transaction.commit(token, options));
            } catch (Exception e) {
                throw new CommitTransactionException(e);
            }
        }

        public static RefundPatpassByWebpayTransactionResponse refund(String token, double amount) throws RefundTransactionException {
            return Transaction.refund(token, amount, null);
        }

        public static RefundPatpassByWebpayTransactionResponse refund(String token, double amount, Options options) throws RefundTransactionException {
            try {
                options = PatpassByWebpay.buildOptions(options);
                return BeanUtils.getInstance().copyBeanData(new RefundPatpassByWebpayTransactionResponse(), WebpayPlus.Transaction.refund(token, amount, options));
            } catch (Exception e) {
                throw new RefundTransactionException(e);
            }
        }

        public static StatusPatpassByWebpayTransactionResponse status(String token) throws StatusTransactionException {
            return Transaction.status(token, null);
        }

        public static StatusPatpassByWebpayTransactionResponse status(String token, Options options) throws StatusTransactionException {
            try {
                options = PatpassByWebpay.buildOptions(options);
                return BeanUtils.getInstance().copyBeanData(new StatusPatpassByWebpayTransactionResponse(), WebpayPlus.Transaction.status(token, options));
            } catch (Exception e) {
                throw new StatusTransactionException(e);
            }
        }
    }

    public static void main(String[] args) {
        System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$tF %1$tT] [%4$-7s] %5$s %n");
        Logger globalLog = Logger.getLogger("cl.transbank");
        globalLog.setUseParentHandlers(false);
        globalLog.addHandler(new ConsoleHandler() {
            {/*setOutputStream(System.out);*/setLevel(Level.ALL);}
        });
        globalLog.setLevel(Level.ALL);

        try {
            String buyOrder = String.valueOf(new Random().nextInt(Integer.MAX_VALUE));
            String sessionId = String.valueOf(new Random().nextInt(Integer.MAX_VALUE));
            String returnUrl = "https://vuelta.com";
            String serviceId = nextString(20);
            String cardHolderId = nextString(20);
            String cardHolderName = nextString(20);
            String cardHolderLastName1 = nextString(20);
            String cardHolderLastName2 = nextString(20);
            String cardHolderMail = String.format("%s@%s.COM", nextString(10), nextString(7));
            String cellphoneNumber = String.valueOf(new Random().nextInt(Integer.MAX_VALUE));
            String expirationDate = "2222-11-11";
            String commerceMail = String.format("%s@%s.COM", nextString(10), nextString(7));
            final CreatePatpassByWebpayTransactionResponse response = Transaction.create(buyOrder, sessionId, 1000, returnUrl, serviceId, cardHolderId, cardHolderName,
                    cardHolderLastName1, cardHolderLastName2, cardHolderMail, cellphoneNumber, expirationDate, commerceMail, false, null);
            logger.info(response.toString());
        } catch (CreateTransactionException e) {
            e.printStackTrace();
        }

        try {
            String token = "e82f8d3efef39449c10c66bc2f121f5af0041e57dff3cec5660e0b2be4251740";
            final CommitPatpassByWebpayTransactionResponse response = Transaction.commit(token);
            logger.info(response.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            String token = "e82f8d3efef39449c10c66bc2f121f5af0041e57dff3cec5660e0b2be4251740";
            final RefundPatpassByWebpayTransactionResponse response = Transaction.refund(token, 10);
            logger.info(response.toString());
        } catch (RefundTransactionException e) {
            e.printStackTrace();
        }

        try {
            String token = "e82f8d3efef39449c10c66bc2f121f5af0041e57dff3cec5660e0b2be4251740";
            final StatusPatpassByWebpayTransactionResponse response = Transaction.status(token);
            logger.info(response.toString());
        } catch (StatusTransactionException e) {
            e.printStackTrace();
        }
    }

    static String nextString(int length) {
        char[] buf = new char[length];
        Random random = new Random();
        final char[] symbols = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        for (int idx = 0; idx < buf.length; ++idx)
            buf[idx] = symbols[random.nextInt(symbols.length)];
        return new String(buf);
    }
}
