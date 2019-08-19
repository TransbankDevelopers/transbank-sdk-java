package cl.transbank.webpay;

import cl.transbank.patpass.PatpassByWebpay;
import cl.transbank.patpass.model.PatpassByWebpayTransactionCommitResponse;
import cl.transbank.patpass.model.PatpassByWebpayTransactionCreateResponse;
import cl.transbank.patpass.model.PatpassByWebpayTransactionRefundResponse;
import cl.transbank.patpass.model.PatpassByWebpayTransactionStatusResponse;
import cl.transbank.transaccioncompleta.FullTransaction;
import cl.transbank.transaccioncompleta.MallFullTransaction;
import cl.transbank.transaccioncompleta.model.*;
import cl.transbank.webpay.exception.*;
import cl.transbank.webpay.oneclick.OneclickMall;
import cl.transbank.webpay.oneclick.OneclickMallDeferred;
import cl.transbank.webpay.oneclick.model.*;
import cl.transbank.webpay.webpayplus.WebpayPlus;
import cl.transbank.webpay.webpayplus.model.*;

import java.io.IOException;
import java.util.Random;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConsoleExamples {
    private static Logger logger = Logger.getLogger(ConsoleExamples.class.getName());

    public static void main(String[] args) {
        System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$tF %1$tT] [%4$-7s] %5$s %n");
        Logger globalLog = Logger.getLogger("cl.transbank");
        globalLog.setUseParentHandlers(false);
        globalLog.addHandler(new ConsoleHandler() {
            {/*setOutputStream(System.out);*/setLevel(Level.ALL);}
        });
        globalLog.setLevel(Level.ALL);

        logger.info("---------------------------- Webpay Plus [Transaction.create] ----------------------------");
        try {
            final WebpayPlusTransactionCreateResponse create = WebpayPlus.Transaction.create("afdgef346456",
                    "432453sdfgdfgh", 1000, "http://localhost:8080");
            logger.info(create.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("");

        logger.info("---------------------------- Webpay Plus [Transaction.commit] ----------------------------");
        {
            String token = "e3308a204a12095590b8f853360dcab92501bf1416f0662186bb31863dec3934";

            try {
                final WebpayPlusTransactionCommitResponse commit = WebpayPlus.Transaction.commit(token);
                logger.info(commit.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            logger.info("");
        }

        logger.info("---------------------------- Webpay Plus [Transaction.refund] ----------------------------");
        {
            String token = "e3308a204a12095590b8f853360dcab92501bf1416f0662186bb31863dec3934";

            try {
                final WebpayPlusTransactionRefundResponse refund = WebpayPlus.Transaction.refund(token, 10);
                logger.info(refund.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            logger.info("");
        }

        logger.info("---------------------------- Webpay Plus [Transaction.status] ----------------------------");
        {
            String token = "e3308a204a12095590b8f853360dcab92501bf1416f0662186bb31863dec3934";

            try {
                final WebpayPlusTransactionStatusResponse status = WebpayPlus.Transaction.status(token);
                logger.info(status.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            logger.info("");
        }

        logger.info("------------------- Webpay Plus Captura Diferida [DeferredTransaction.create] -------------------");
        try {
            String buyOrder = "afdgef346456";
            final WebpayPlusTransactionCreateResponse deferredCapture = WebpayPlus.DeferredTransaction.create(buyOrder,
                    "432453sdfgdfgh", 1000, "http://localhost:8080");
            logger.info(deferredCapture.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("");

        logger.info("------------------- Webpay Plus Captura Diferida [DeferredTransaction.commit] -------------------");
        {
            String token = "e4a2ba3e6cac3e8c6b2fe4bb5556c6245f6e19888f2879d885dc460377657a0b";

            try {
                final WebpayPlusTransactionCommitResponse commit = WebpayPlus.DeferredTransaction.commit(token);
                logger.info(commit.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            logger.info("");
        }

        logger.info("------------------- Webpay Plus Captura Diferida [DeferredTransaction.capture] -------------------");
        {
            try {
                String token = "ec113a08b4042a04f76c9055017807833cccf8b710c66f584ae6c6ee687135c9";
                String buyOrder = "121225613";
                String authorizationCode = "1213";
                final WebpayPlusTransactionCaptureResponse capture = WebpayPlus.DeferredTransaction.capture(token, buyOrder, authorizationCode, 1000);
                logger.info(capture.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            logger.info("");
        }

        logger.info("-------------------- Webpay Plus Captura Diferida [DeferredTransaction.refund] --------------------");
        {
            String token = "e57a9aacde782fee299e3cb3ba2c93ec8d1aa4a388ea85647e1ec5ebddb4bc7a";

            try {
                final WebpayPlusTransactionRefundResponse refund = WebpayPlus.DeferredTransaction.refund(token, 10);
                logger.info(refund.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            logger.info("");
        }

        logger.info("-------------------- Webpay Plus Captura Diferida [DeferredTransaction.status] --------------------");
        {
            String token = "e57a9aacde782fee299e3cb3ba2c93ec8d1aa4a388ea85647e1ec5ebddb4bc7a";

            try {
                final WebpayPlusTransactionStatusResponse status = WebpayPlus.DeferredTransaction.status(token);
                logger.info(status.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            logger.info("");
        }

        logger.info("-------------------------- Webpay Plus Mall [Transaction.create] --------------------------");
        try {
            final WebpayPlusMallTransactionCreateResponse create = WebpayPlus.MallTransaction.create("afdgef346456",
                    "432453sdfgdfgh", "http://localhost:8080", cl.transbank.model.MallTransactionCreateDetails.build()
                            .add(1000, "597055555536", "r234n347"));
            logger.info(create.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("");

        logger.info("-------------------------- Webpay Plus Mall Mall [Transaction.commit] --------------------------");
        {
            String token = "e3f039e2edd7554e8f7e82fe0e510d325e2e39c7d144cf6c329718430270b7bf";

            try {
                final WebpayPlusMallTransactionCommitResponse commit = WebpayPlus.MallTransaction.commit(token);
                logger.info(commit.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            logger.info("");
        }

        logger.info("---------------------------- Webpay Plus Mall [Transaction.refund] ----------------------------");
        {
            String token = "e057e96c3653d24cc8224535a9609e890ce4f55a25140a86f0438a4bf1b57d04";

            try {
                String buyOrder = "494887257";
                String commerceCode = "597055555536";
                final WebpayPlusTransactionRefundResponse refund = WebpayPlus.MallTransaction.refund(token, buyOrder, commerceCode, 1000);
                logger.info(refund.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            logger.info("");
        }

        logger.info("-------------------- Webpay Plus Mall [Transaction.status] --------------------");
        {
            String token = "e057e96c3653d24cc8224535a9609e890ce4f55a25140a86f0438a4bf1b57d04";

            try {
                final WebpayPlusMallTransactionStatusResponse status = WebpayPlus.MallTransaction.status(token);
                logger.info(status.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            logger.info("");
        }

        logger.info("------------------ Webpay Plus Mall Deferred [MallDeferredTransaction.create] ------------------");
        {
            try {
                final cl.transbank.model.MallTransactionCreateDetails details = cl.transbank.model.MallTransactionCreateDetails.build()
                        .add(1000, "597055555546", "12352346435")
                        .add(1000, "597055555545", "098765432");
                final WebpayPlusMallTransactionCreateResponse create = WebpayPlus.MallDeferredTransaction.create("234645645645",
                        "432453sdfgdfgh", "http://localhost:8080", details);
                logger.info(create.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            logger.info("");
        }

        logger.info("-------------------------- Webpay Plus Mall Deferred [MallDeferredTransaction.commit] --------------------------");
        {
            String token = "e002a8b38b8350e0fd68fa016ed5e430018547c71707acd12b222fa747e302cc";

            try {
                final WebpayPlusMallTransactionCommitResponse commit = WebpayPlus.MallDeferredTransaction.commit(token);
                logger.info(commit.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            logger.info("");
        }

        logger.info("------------------- Webpay Plus Mall Deferred [MallDeferredTransaction.capture] -------------------");
        {
            try {
                String token = "e002a8b38b8350e0fd68fa016ed5e430018547c71707acd12b222fa747e302cc";
                String commerceCode = "597055555546";
                String buyOrder = "12352346435";
                String authorizationCode = "1213";
                final WebpayPlusMallTransactionCaptureResponse capture = WebpayPlus.MallDeferredTransaction.capture(token, commerceCode, buyOrder, authorizationCode, 1000);
                logger.info(capture.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            logger.info("");
        }

        logger.info("---------------------------- Webpay Plus Mall Deferred [MallDeferredTransaction.refund] ----------------------------");
        {
            String token = "e5c6b2fda0ff0dd8fded9c811d97afb5927aff8d446be56bac6c0e14fa6ca0ba";

            try {
                String buyOrder = "12352346435";
                String commerceCode = "597055555546";
                final WebpayPlusTransactionRefundResponse refund = WebpayPlus.MallDeferredTransaction.refund(token, buyOrder, commerceCode, 1000);
                logger.info(refund.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            logger.info("");
        }

        logger.info("-------------------- Webpay Plus Mall Deferred [MallDeferredTransaction.status] --------------------");
        {
            String token = "e002a8b38b8350e0fd68fa016ed5e430018547c71707acd12b222fa747e302cc";

            try {
                final WebpayPlusMallTransactionStatusResponse status = WebpayPlus.MallDeferredTransaction.status(token);
                logger.info(status.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            logger.info("");
        }

        logger.info("---------------------------- OneclickMall Mall [Inscription.start] ----------------------------");
        {
            try {
                final OneclickMallInscriptionStartResponse inscriptionStart =
                        OneclickMall.Inscription.start("Nombreusuario", "correo@casdasd.cl", "http://some.url/return");
                logger.info(inscriptionStart.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            logger.info("");
        }

        logger.info("---------------------------- OneclickMall Mall [Inscription.finish] ----------------------------");
        {
            String token = "ea98d8c01c74723015834cd801ec9b6d1226f745ff292daa52ec38198b40e23f";

            try {
                final OneclickMallInscriptionFinishResponse finish = OneclickMall.Inscription.finish(token);
                logger.info(finish.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            logger.info("");
        }

        {
            String buyOrder = String.valueOf(new Random().nextInt(Integer.MAX_VALUE));
            String buyOrderOne = String.valueOf(new Random().nextInt(Integer.MAX_VALUE));
            String buyOrderTwo = String.valueOf(new Random().nextInt(Integer.MAX_VALUE));
            String username = "goncafa";
            String tbkUser = "f1e9252c-b9c4-4e39-9c45-182d3b157818";

            logger.info("---------------------------- OneclickMall Mall [Transaction.autorize] ----------------------------");
            {
                try {
                    String mallOne = "597055555542";
                    String mallTwo = "597055555543";
                    cl.transbank.webpay.oneclick.model.MallTransactionCreateDetails details = cl.transbank.webpay.oneclick.model.MallTransactionCreateDetails.build()
                            .add(1000, mallOne, buyOrderOne, (byte) 1)
                            .add(1000, mallTwo, buyOrderTwo, (byte) 1);
                    final OneclickMallTransactionAuthorizeResponse authorize = OneclickMall.Transaction.authorize(username, tbkUser, buyOrder, details);
                    logger.info(authorize.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                logger.info("");
            }

            logger.info("---------------------------- OneclickMall Mall [Transaction.refund] ----------------------------");
            {
                try {
                    double amount = 1000;
                    String childCommerceCode = "597055555542";

                    final OneclickMallTransactionRefundResponse refund = OneclickMall.Transaction.refund(buyOrder, childCommerceCode, buyOrderOne, amount);
                    logger.info(refund.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                logger.info("");
            }

            logger.info("---------------------------- OneclickMall Mall [Transaction.status] ----------------------------");
            {
                try {
                    final OneclickMallTransactionStatusResponse status = OneclickMall.Transaction.status(buyOrder);
                    logger.info(status.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                logger.info("");
            }

            logger.info("---------------------------- OneclickMall Mall [Inscription.delete] ----------------------------");
            {
                try {
                    OneclickMall.Inscription.delete(username, tbkUser);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                logger.info("");
            }
        }

        {
            logger.info("---------------------------- OneclickMall Mall Deferred Capture [Inscription.start] ----------------------------");
            {
                try {
                    final OneclickMallInscriptionStartResponse inscriptionStart =
                            OneclickMallDeferred.Inscription.start("Nombreusuario", "correo@casdasd.cl", "http://some.url/return");
                    logger.info(inscriptionStart.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                logger.info("");
            }

            logger.info("---------------------------- OneclickMall Mall Deferred Capture [Inscription.finish] ----------------------------");
            {
                String token = "e95492790d1199edd91804eba46f94e92647b2afe76a98c99f7121ad5758f630";

                try {
                    final OneclickMallInscriptionFinishResponse finish = OneclickMallDeferred.Inscription.finish(token);
                    logger.info(finish.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                logger.info("");
            }

            String buyOrder = String.valueOf(new Random().nextInt(Integer.MAX_VALUE));
            String buyOrderOne = String.valueOf(new Random().nextInt(Integer.MAX_VALUE));
            String buyOrderTwo = String.valueOf(new Random().nextInt(Integer.MAX_VALUE));
            String username = "goncafadeferred";
            String tbkUser = "7bd7fde2-e1ad-40e0-ab72-476930efd979";
            String mallOne = "597055555548";
            String mallTwo = "597055555549";

            logger.info("---------------------------- OneclickMall Mall Deferred Capture [Transaction.autorize] ----------------------------");
            {
                try {
                    cl.transbank.webpay.oneclick.model.MallTransactionCreateDetails details = cl.transbank.webpay.oneclick.model.MallTransactionCreateDetails.build()
                            .add(1000, mallOne, buyOrderOne, (byte) 1)
                            .add(1000, mallTwo, buyOrderTwo, (byte) 1);
                    final OneclickMallTransactionAuthorizeResponse authorize = OneclickMallDeferred.Transaction.authorize(username, tbkUser, buyOrder, details);
                    logger.info(authorize.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                logger.info("");
            }

            logger.info("---------------------------- OneclickMall Mall Deferred Capture [Transaction.refund] ----------------------------");
            {
                try {
                    double amount = 1000;

                    final OneclickMallTransactionRefundResponse refund = OneclickMallDeferred.Transaction.refund(buyOrder, mallOne, buyOrderOne, amount);
                    logger.info(refund.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                logger.info("");
            }

            logger.info("---------------------------- OneclickMall Mall Deferred Capture [Transaction.status] ----------------------------");
            {
                try {
                    final OneclickMallTransactionStatusResponse status = OneclickMallDeferred.Transaction.status(buyOrder);
                    logger.info(status.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                logger.info("");
            }

            logger.info("---------------------------- OneclickMall Mall Deferred Capture [Inscription.delete] ----------------------------");
            {
                try {
                    OneclickMallDeferred.Inscription.delete(username, tbkUser);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                logger.info("");
            }
        }

        logger.info("---------------------------- Pattpass By Webpay Create [PatpassByWebpay.Transaction.create] ----------------------------");
        {
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
                final PatpassByWebpayTransactionCreateResponse response = PatpassByWebpay.Transaction.create(buyOrder, sessionId, 1000, returnUrl, serviceId, cardHolderId, cardHolderName,
                        cardHolderLastName1, cardHolderLastName2, cardHolderMail, cellphoneNumber, expirationDate, commerceMail, false, null);
                logger.info(response.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        logger.info("---------------------------- Pattpass By Webpay Commit [PatpassByWebpay.Transaction.commit] ----------------------------");
        {
            try {
                String token = "e82f8d3efef39449c10c66bc2f121f5af0041e57dff3cec5660e0b2be4251740";
                final PatpassByWebpayTransactionCommitResponse response = PatpassByWebpay.Transaction.commit(token);
                logger.info(response.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        logger.info("---------------------------- Pattpass By Webpay Refund [PatpassByWebpay.Transaction.refund] ----------------------------");
        {

            try {
                String token = "e82f8d3efef39449c10c66bc2f121f5af0041e57dff3cec5660e0b2be4251740";
                final PatpassByWebpayTransactionRefundResponse response = PatpassByWebpay.Transaction.refund(token, 10);
                logger.info(response.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        logger.info("---------------------------- Pattpass By Webpay Status [PatpassByWebpay.Transaction.status] ----------------------------");
        {
            try {
                String token = "e82f8d3efef39449c10c66bc2f121f5af0041e57dff3cec5660e0b2be4251740";
                final PatpassByWebpayTransactionStatusResponse response = PatpassByWebpay.Transaction.status(token);
                logger.info(response.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        logger.info("---------------------------- FullTransaction  [FullTransaction.Transaction.create] ----------------------------");
        {
            String buyOrder = String.valueOf(new Random().nextInt(Integer.MAX_VALUE));
            String sessionId = String.valueOf(new Random().nextInt(Integer.MAX_VALUE));
            double amount = 10000;
            String cardNumber= "4051885600446623";
            String cardExpirationDate= "23/03";
            short cvv = 123;
            try {
                FullTransactionCreateResponse response = FullTransaction.Transaction.create(buyOrder, sessionId, amount, cardNumber, cardExpirationDate, cvv);
                System.out.println(response.toString());
            } catch (TransactionCreateException | IOException e) {
                e.printStackTrace();
            }
        }
        logger.info("---------------------------- FullTransaction  [FullTransaction.Transaction.installment] ----------------------------");
        {
            String token = "e966c9b10a4e6c7c7ac79512baf18173ecfaf44c9aeb8ebb05173077b6ad8a85";
            byte installmentsNumber = 12;
            try {
                FullTransactionInstallmentResponse response = FullTransaction.Transaction.installment(token, installmentsNumber);
                System.out.println(response.toString());
            } catch (TransactionInstallmentException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        logger.info("---------------------------- FullTransaction  [FullTransaction.Transaction.commit] ----------------------------");
        {
            String token = "e966c9b10a4e6c7c7ac79512baf18173ecfaf44c9aeb8ebb05173077b6ad8a85";
            Long idQueryInstallments = 17325868L;
            byte deferredPeriodIndex= 1;
            Boolean gracePeriod = false;
            try {
                FullTransactionCommitResponse response = FullTransaction.Transaction.commit(token,idQueryInstallments,deferredPeriodIndex,gracePeriod);
                System.out.println(response.toString());
            } catch (TransactionCommitException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        logger.info("---------------------------- FullTransaction  [FullTransaction.Transaction.status] ----------------------------");
        {
            String token = "e966c9b10a4e6c7c7ac79512baf18173ecfaf44c9aeb8ebb05173077b6ad8a85";
            try {
                FullTransactionStatusResponse response = FullTransaction.Transaction.status(token);
                System.out.println(response.toString());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TransactionStatusException e) {
                e.printStackTrace();
            }
        }

        logger.info("---------------------------- FullTransaction  [FullTransaction.Transaction.refund] ----------------------------");
        {
            String token = "e966c9b10a4e6c7c7ac79512baf18173ecfaf44c9aeb8ebb05173077b6ad8a85";
            double amount = 1000;
            try {
                FullTransactionRefundResponse response = FullTransaction.Transaction.refund(token,amount);
                System.out.println(response.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }  catch (TransactionRefundException e) {
                e.printStackTrace();
            }
        }

        logger.info("---------------------------- FullTransaction Mall  [MallFullTransaction.Transaction.create] ----------------------------");
        {
            String buyOrder = String.valueOf(new Random().nextInt(Integer.MAX_VALUE));
            String sessionId = String.valueOf(new Random().nextInt(Integer.MAX_VALUE));
            double amount = 10000;
            String cardNumber= "4051885600446623";
            String cardExpirationDate= "23/03";
            short cvv = 123;
            String commerceCode ="597055555552";
            String commerceCode2 ="597055555553";
            String buyOrder1 = String.valueOf(new Random().nextInt(Integer.MAX_VALUE));
            String buyOrder2 = String.valueOf(new Random().nextInt(Integer.MAX_VALUE));

            cl.transbank.model.MallTransactionCreateDetails details = cl.transbank.model.MallTransactionCreateDetails.build().add(amount,commerceCode,buyOrder1).add(amount,commerceCode2,buyOrder2);
            try {
                MallFullTransactionCreateResponse response = MallFullTransaction.Transaction.create(buyOrder, sessionId, cardNumber, cardExpirationDate, details);
                System.out.println(response.toString());
            } catch (TransactionCreateException | IOException e) {
                e.printStackTrace();
            }
        }
        logger.info("---------------------------- FullTransaction Mall  [MallFullTransaction.Transaction.installment] ----------------------------");
        {
            String token="eb501f2f1000c11ed105492c8bc702688f7679c95148bf556a66da627bb0a761";
            byte installmentsNumber = 12;
            String commerceCode = "597055555552";
            String buyOrder="94589433";
            double amount = 1000;
            long idQueryInstallments = 17444420L;
            try {
                FullTransactionInstallmentResponse response = MallFullTransaction.Transaction.installment(token, commerceCode, buyOrder, installmentsNumber, null);
                System.out.println(response.toString());
                idQueryInstallments = response.getIdQueryInstallments();
            } catch (TransactionInstallmentException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        logger.info("---------------------------- FullTransaction Mall  [MallFullTransaction.Transaction.commit] ----------------------------");
        {
            byte deferredPeriodIndex= 1;
            Boolean gracePeriod = false;
            String commerceCode = "597055555552";
            String buyOrder="94589433";
            long idQueryInstallments = 17444420L;
            String token="eb501f2f1000c11ed105492c8bc702688f7679c95148bf556a66da627bb0a761";
            try {
                MallTransactionCommitDetails details2 = MallTransactionCommitDetails.build().add(commerceCode,buyOrder,idQueryInstallments,deferredPeriodIndex,gracePeriod);
                MallFullTransactionCommitResponse response = MallFullTransaction.Transaction.commit(token,details2);
                System.out.println(response.toString());
            } catch (TransactionCommitException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        logger.info("---------------------------- FullTransaction Mall  [MallFullTransaction.Transaction.status] ----------------------------");
        {
            String token = "e1c942d119cfe3f7ca94c677ef267e9ab87165d50a41178f12232bd67245146a";

                try {
                    MallFullTransactionStatusResponse response = MallFullTransaction.Transaction.status(token);
                    System.out.println(response.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (TransactionStatusException e) {
                    e.printStackTrace();
                }
        }
        logger.info("---------------------------- FullTransaction Mall  [MallFullTransaction.Transaction.refund] ----------------------------");
        {
            String token = "e26ae6e0d76b729747b2ca7d758cb0ffddc03b1a4e5b6f6e3deebb3ab35cfa3e";
            double amount = 1000;
            String commerceCode = "597055555552";
            String buyOrder = "r234n347";
            try {
                MallFullTransactionRefundResponse response = MallFullTransaction.Transaction.refund(token, amount, commerceCode, buyOrder);
                System.out.println(response.toString());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TransactionRefundException e) {
                e.printStackTrace();
            }
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
