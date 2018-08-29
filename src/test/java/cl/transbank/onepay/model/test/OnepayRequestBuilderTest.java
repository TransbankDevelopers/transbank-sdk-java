package cl.transbank.onepay.model.test;

import cl.transbank.exception.TransbankException;
import cl.transbank.onepay.Onepay;
import cl.transbank.onepay.model.*;
import cl.transbank.onepay.net.SendTransactionRequest;
import cl.transbank.onepay.util.OnepayRequestBuilder;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class OnepayRequestBuilderTest {

    public ShoppingCart createCart() throws TransbankException{
        // Setting items to the shopping cart
        ShoppingCart cart = new ShoppingCart();
        cart.add(new Item("Zapatos", 1, 15000, null, -1));
        cart.add(new Item("Pantalon", 1, 12500, null, -1));
        return cart;
    }

    public Options createOptions(){
        return new Options()
                .setApiKey("mUc0GxYGor6X8u-_oB3e-HWJulRG01WoC96-_tUA3Bg")
                .setSharedSecret("P4DCPS55QB2QLT56SQH6#W#LV76IAPYX");
    }

    @Before
    public void setUp() {
        Onepay.setIntegrationType(Onepay.IntegrationType.MOCK);
    }

    @Test
    public void testOnepayRequestBuilderSendTransactionRequest()
            throws TransbankException {
        Onepay.setCallbackUrl("https://something");
        ShoppingCart cart = createCart();
        String externalUniqueNumber = "123-456-789";
        SendTransactionRequest request = OnepayRequestBuilder.getInstance().buildSendTransactionRequest(cart,Onepay.Channel.WEB,
                externalUniqueNumber, createOptions());
        assertEquals("mUc0GxYGor6X8u-_oB3e-HWJulRG01WoC96-_tUA3Bg", request.getApiKey());
        assertEquals("04533c31-fe7e-43ed-bbc4-1c8ab1538afp", request.getAppKey());
        assertEquals("https://something", request.getCallbackUrl());
        assertEquals("WEB", request.getChannel());
        assertEquals("123-456-789", request.getExternalUniqueNumber());
        assertEquals(2, request.getItems().size());
        assertEquals(2, request.getItemsQuantity());
        assertEquals(27500, request.getTotal());
    }

    @Test(expected = NullPointerException.class)
    public void testOnepayRequestBuilderSendTransactionRequestNullCart()
            throws TransbankException {
        SendTransactionRequest request = OnepayRequestBuilder.getInstance().buildSendTransactionRequest(null, Onepay.Channel.WEB, "1", createOptions());
    }

    @Test(expected = NullPointerException.class)
    public void testOnepayRequestBuilderSendTransactionRequestNullChannel()
            throws TransbankException {
        ShoppingCart cart = createCart();
        SendTransactionRequest request = OnepayRequestBuilder.getInstance().buildSendTransactionRequest(cart, null, "1", createOptions());
    }

    @Test(expected = NullPointerException.class)
    public void testOnepayRequestBuilderSendTransactionRequestNullEUN()
            throws TransbankException {
        ShoppingCart cart = createCart();
        SendTransactionRequest request = OnepayRequestBuilder.getInstance().buildSendTransactionRequest(cart, Onepay.Channel.WEB, null, createOptions());
    }

    @Test(expected = NullPointerException.class)
    public void testOnepayRequestBuilderSendTransactionRequestNullOptions()
            throws TransbankException {
        ShoppingCart cart = createCart();
        SendTransactionRequest request = OnepayRequestBuilder.getInstance().buildSendTransactionRequest(cart, Onepay.Channel.WEB, "1", null);
    }
}
