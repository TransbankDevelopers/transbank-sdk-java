package cl.transbank.webpay;

import cl.transbank.transaccioncompleta.FullTransaction;
import cl.transbank.transaccioncompleta.model.*;
import cl.transbank.webpay.exception.*;
import cl.transbank.webpay.oneclick.OneclickMall;
import cl.transbank.webpay.oneclick.model.*;

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
