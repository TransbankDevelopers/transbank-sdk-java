package cl.transbank.webpay;

import cl.transbank.webpay.oneclick.OneclickMall;
import cl.transbank.webpay.oneclick.OneclickMallDeferred;
import cl.transbank.webpay.oneclick.model.*;
import cl.transbank.webpay.webpayplus.WebpayPlus;
import cl.transbank.webpay.webpayplus.model.*;
import cl.transbank.webpay.webpayplus.model.CreateMallTransactionDetails;

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
            final CreateWebpayPlusTransactionResponse create = WebpayPlus.Transaction.create("afdgef346456",
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
                final CommitWebpayPlusTransactionResponse commit = WebpayPlus.Transaction.commit(token);
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
                final RefundWebpayPlusTransactionResponse refund = WebpayPlus.Transaction.refund(token, 10);
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
                final StatusWebpayPlusTransactionResponse status = WebpayPlus.Transaction.status(token);
                logger.info(status.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            logger.info("");
        }

        logger.info("------------------- Webpay Plus Captura Diferida [DeferredTransaction.create] -------------------");
        try {
            String buyOrder = "afdgef346456";
            final CreateWebpayPlusTransactionResponse deferredCapture = WebpayPlus.DeferredTransaction.create(buyOrder,
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
                final CommitWebpayPlusTransactionResponse commit = WebpayPlus.DeferredTransaction.commit(token);
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
                final CaptureWebpayPlusTransactionResponse capture = WebpayPlus.DeferredTransaction.capture(token, buyOrder, authorizationCode, 1000);
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
                final RefundWebpayPlusTransactionResponse refund = WebpayPlus.DeferredTransaction.refund(token, 10);
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
                final StatusWebpayPlusTransactionResponse status = WebpayPlus.DeferredTransaction.status(token);
                logger.info(status.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            logger.info("");
        }

        logger.info("-------------------------- Webpay Plus Mall [Transaction.create] --------------------------");
        try {
            final CreateWebpayPlusMallTransactionResponse create = WebpayPlus.MallTransaction.create("afdgef346456",
                    "432453sdfgdfgh", "http://localhost:8080", CreateMallTransactionDetails.build(
                            1000, "597055555536", "r234n347"));
            logger.info(create.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("");

        logger.info("-------------------------- Webpay Plus Mall Mall [Transaction.commit] --------------------------");
        {
            String token = "e3f039e2edd7554e8f7e82fe0e510d325e2e39c7d144cf6c329718430270b7bf";

            try {
                final CommitWebpayPlusMallTransactionResponse commit = WebpayPlus.MallTransaction.commit(token);
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
                final RefundWebpayPlusTransactionResponse refund = WebpayPlus.MallTransaction.refund(token, buyOrder, commerceCode, 1000);
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
                final StatusWebpayPlusMallTransactionResponse status = WebpayPlus.MallTransaction.status(token);
                logger.info(status.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            logger.info("");
        }

        logger.info("------------------ Webpay Plus Mall Deferred [MallDeferredTransaction.create] ------------------");
        {
            try {
                final CreateMallTransactionDetails details = CreateMallTransactionDetails.build()
                        .add(1000, "597055555546", "12352346435")
                        .add(1000, "597055555545", "098765432");
                final CreateWebpayPlusMallTransactionResponse create = WebpayPlus.MallDeferredTransaction.create("234645645645",
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
                final CommitWebpayPlusMallTransactionResponse commit = WebpayPlus.MallDeferredTransaction.commit(token);
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
                final CaptureWebpayPlusMallTransactionResponse capture = WebpayPlus.MallDeferredTransaction.capture(token, commerceCode, buyOrder, authorizationCode, 1000);
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
                final RefundWebpayPlusTransactionResponse refund = WebpayPlus.MallDeferredTransaction.refund(token, buyOrder, commerceCode, 1000);
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
                final StatusWebpayPlusMallTransactionResponse status = WebpayPlus.MallDeferredTransaction.status(token);
                logger.info(status.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            logger.info("");
        }

        logger.info("---------------------------- OneclickMall Mall [Inscription.start] ----------------------------");
        {
            try {
                final StartOneclickMallInscriptionResponse inscriptionStart =
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
                final FinishOneclickMallInscriptionResponse finish = OneclickMall.Inscription.finish(token);
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
                    cl.transbank.webpay.oneclick.model.CreateMallTransactionDetails details = cl.transbank.webpay.oneclick.model.CreateMallTransactionDetails.build()
                            .add(1000, mallOne, buyOrderOne, (byte) 1)
                            .add(1000, mallTwo, buyOrderTwo, (byte) 1);
                    final AuthorizeOneclickMallTransactionResponse authorize = OneclickMall.Transaction.authorize(username, tbkUser, buyOrder, details);
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

                    final RefundOneclickMallTransactionResponse refund = OneclickMall.Transaction.refund(buyOrder, childCommerceCode, buyOrderOne, amount);
                    logger.info(refund.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                logger.info("");
            }

            logger.info("---------------------------- OneclickMall Mall [Transaction.status] ----------------------------");
            {
                try {
                    final StatusOneclickMallTransactionResponse status = OneclickMall.Transaction.status(buyOrder);
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
                    final StartOneclickMallInscriptionResponse inscriptionStart =
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
                    final FinishOneclickMallInscriptionResponse finish = OneclickMallDeferred.Inscription.finish(token);
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

            logger.info("---------------------------- OneclickMall Mall [Transaction.autorize] ----------------------------");
            {
                try {
                    cl.transbank.webpay.oneclick.model.CreateMallTransactionDetails details = cl.transbank.webpay.oneclick.model.CreateMallTransactionDetails.build()
                            .add(1000, mallOne, buyOrderOne, (byte) 1)
                            .add(1000, mallTwo, buyOrderTwo, (byte) 1);
                    final AuthorizeOneclickMallTransactionResponse authorize = OneclickMallDeferred.Transaction.authorize(username, tbkUser, buyOrder, details);
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

                    final RefundOneclickMallTransactionResponse refund = OneclickMallDeferred.Transaction.refund(buyOrder, mallOne, buyOrderOne, amount);
                    logger.info(refund.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                logger.info("");
            }

            logger.info("---------------------------- OneclickMall Mall [Transaction.status] ----------------------------");
            {
                try {
                    final StatusOneclickMallTransactionResponse status = OneclickMallDeferred.Transaction.status(buyOrder);
                    logger.info(status.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                logger.info("");
            }

            logger.info("---------------------------- OneclickMall Mall [Inscription.delete] ----------------------------");
            {
                try {
                    OneclickMallDeferred.Inscription.delete(username, tbkUser);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                logger.info("");
            }
        }
    }
}
