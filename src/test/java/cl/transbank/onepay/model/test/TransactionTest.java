package cl.transbank.onepay.model.test;

import cl.transbank.onepay.Onepay;
import cl.transbank.exception.TransbankException;
import cl.transbank.onepay.exception.TransactionCreateException;
import cl.transbank.onepay.model.*;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static org.junit.Assert.*;

public class TransactionTest {

    private Logger log = LoggerFactory.getLogger(TransactionTest.class);
    private static final String EXTERNAL_UNIQUE_NUMBER_TO_COMMIT_TRANSACTION_TEST = "f506a955-800c-4185-8818-4ef9fca97aae";
    private static final String OCC_TO_COMMIT_TRANSACTION_TEST = "1807983490979289";

    @Before
    public void setup(){
        // Setting comerce data
        Onepay.setSharedSecret("P4DCPS55QB2QLT56SQH6#W#LV76IAPYX");
        Onepay.setApiKey("mUc0GxYGor6X8u-_oB3e-HWJulRG01WoC96-_tUA3Bg");
        Onepay.setCallbackUrl("http://www.somecallback.com/example");
        Onepay.setAppScheme("schemetest");
        Onepay.setQrWidthHeight(200);
        Onepay.setCommerceLogoUrl("http://www.google.cl?q=vegeta");
        Onepay.setIntegrationType(Onepay.IntegrationType.MOCK);
    }

    public ShoppingCart createCart() throws TransbankException{
        // Setting items to the shopping cart
        ShoppingCart cart = new ShoppingCart();
        cart.add(new Item("Zapatos", 1, 15000, null, -1));
        cart.add(new Item("Pantalon", 1, 12500, null, -1));
        return cart;
    }

    @Test
    public void testTransactionCreate()
            throws IOException, TransbankException {
        // Send transaction to Transbank
        TransactionCreateResponse response = Transaction.create(createCart(), Onepay.Channel.APP);
        assertNotNull(response);

        // Print response
        log.debug(response.toString());
    }

    @Test
    public void testTransactionCreateCustomEUN()
        throws IOException, TransbankException {
        String externalUniqueNumber = "1234-456-789";
        TransactionCreateResponse response = Transaction.create(createCart(), Onepay.Channel.WEB, externalUniqueNumber);
        assertNotNull(response);
    }

    @Test
    public void testTransactionCommit()
            throws IOException, TransbankException {

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
