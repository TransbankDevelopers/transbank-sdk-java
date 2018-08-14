package cl.transbank.onepay.model.test;

import cl.transbank.onepay.Onepay;
import cl.transbank.exception.TransbankException;
import cl.transbank.onepay.model.*;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static org.junit.Assert.*;

public class TransactionTest {

    private Logger log = LoggerFactory.getLogger(TransactionTest.class);
    private static final String EXTERNAL_UNIQUE_NUMBER_TO_COMMIT_TRANSACTION_TEST = "f506a955-800c-4185-8818-4ef9fca97aae";
    private static final String OCC_TO_COMMIT_TRANSACTION_TEST = "1807983490979289";

    @Test
    public void testTransactionCreate()
            throws IOException, TransbankException {
        Onepay.setIntegrationType(Onepay.IntegrationType.MOCK);

        // Setting comerce data
        Onepay.setSharedSecret("P4DCPS55QB2QLT56SQH6#W#LV76IAPYX");
        Onepay.setApiKey("mUc0GxYGor6X8u-_oB3e-HWJulRG01WoC96-_tUA3Bg");
        Onepay.setCallbackUrl("http://www.somecallback.com/example");

        // Setting items to the shopping cart
        ShoppingCart cart = new ShoppingCart();
        cart.add(new Item("Zapatos", 1, 15000, null, -1));
        cart.add(new Item("Pantalon", 1, 12500, null, -1));

        // Send transaction to Transbank
        TransactionCreateResponse response = Transaction.create(cart);

        assertNotNull(response);

        // Print response
        log.debug(response.toString());
    }

    @Test
    public void testTransactionCommit()
            throws IOException, TransbankException {
        Onepay.setIntegrationType(Onepay.IntegrationType.MOCK);

        // Setting comerce data
        Options options = new Options()
                .setApiKey("mUc0GxYGor6X8u-_oB3e-HWJulRG01WoC96-_tUA3Bg")
                .setSharedSecret("P4DCPS55QB2QLT56SQH6#W#LV76IAPYX");

        // commit transaction
        TransactionCommitResponse response = Transaction.commit(OCC_TO_COMMIT_TRANSACTION_TEST,
                EXTERNAL_UNIQUE_NUMBER_TO_COMMIT_TRANSACTION_TEST, options);

        assertNotNull(response);

        log.debug(response.toString());
    }
}
