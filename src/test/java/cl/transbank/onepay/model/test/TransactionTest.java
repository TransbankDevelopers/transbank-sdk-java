package cl.transbank.onepay.model.test;

import cl.transbank.onepay.Onepay;
import cl.transbank.onepay.exception.AmountException;
import cl.transbank.onepay.exception.SignException;
import cl.transbank.onepay.exception.TransactionCreateException;
import cl.transbank.onepay.exception.TransbankException;
import cl.transbank.onepay.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class TransactionTest {

    private Logger log = LoggerFactory.getLogger(TransactionTest.class);
    private static final String EXTERNAL_UNIQUE_NUMBER_TO_COMMIT_TRANSACTION_TEST = "8934751b-aa9a-45be-b686-1f45b6c45b02";
    private static final String OCC_TO_COMMIT_TRANSACTION_TEST = "1807419329781765";

    public void testSendTransactionOneWay()
            throws IOException, TransbankException {
        // Setting comerce data
        Onepay.setSharedSecret("P4DCPS55QB2QLT56SQH6#W#LV76IAPYX");
        Onepay.setApiKey("mUc0GxYGor6X8u-_oB3e-HWJulRG01WoC96-_tUA3Bg");
        Onepay.setAppKey("04533c31-fe7e-43ed-bbc4-1c8ab1538afp");
        Onepay.setCallbackUrl("http://localhost:8080/ewallet-endpoints");

        // Setting items to the shopping cart
        ShoppingCart cart = new ShoppingCart();
        cart.add(new Item("Zapatos", 1, 15000, null, -1));
        cart.add(new Item("Pantalon", 1, 12500, null, -1));

        // Send transaction to Transbank
        TransactionCreateResponse response = Transaction.create(cart);

        assert null != response;

        // Print response
        log.debug(response.toString());
    }

    public void testSendTransactionSecondWay()
            throws IOException, TransbankException {
        // Setting comerce data
        Onepay.setCallbackUrl("http://localhost:8080/ewallet-endpoints");

        // Setting items to the shopping cart
        ShoppingCart cart = new ShoppingCart();
        cart.add(new Item("Porotos Wasil", 1, 990, null, -1));
        cart.add(new Item("Confort", 1, 1500, null, -1));

        // Setting comerce data
        Options options = new Options()
                .setApiKey("mUc0GxYGor6X8u-_oB3e-HWJulRG01WoC96-_tUA3Bg")
                .setAppKey("04533c31-fe7e-43ed-bbc4-1c8ab1538afp")
                .setSharedSecret("P4DCPS55QB2QLT56SQH6#W#LV76IAPYX");
        // Send transaction to Transbank
        TransactionCreateResponse response = Transaction.create(cart, options);


        assert null != response;

        // Print response
        log.debug(response.toString());
    }

    public void testTransactionCommit()
            throws IOException, TransbankException {
        // Setting comerce data
        Onepay.setCallbackUrl("http://localhost:8080/ewallet-endpoints");

        // Setting comerce data
        Options options = new Options()
                .setApiKey("mUc0GxYGor6X8u-_oB3e-HWJulRG01WoC96-_tUA3Bg")
                .setAppKey("04533c31-fe7e-43ed-bbc4-1c8ab1538afp")
                .setSharedSecret("P4DCPS55QB2QLT56SQH6#W#LV76IAPYX");

        // commit transaction
        TransactionCommitResponse response = Transaction.commit(OCC_TO_COMMIT_TRANSACTION_TEST,
                EXTERNAL_UNIQUE_NUMBER_TO_COMMIT_TRANSACTION_TEST, options);

        assert null != response;
        log.debug(response.toString());
    }
}
