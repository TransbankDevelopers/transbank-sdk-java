package cl.transbank.onepay.model.test;

import cl.transbank.exception.TransbankException;
import cl.transbank.onepay.Onepay;
import cl.transbank.onepay.model.Item;
import cl.transbank.onepay.model.ShoppingCart;
import cl.transbank.onepay.model.Transaction;
import org.junit.Assert;
import org.junit.Test;

public class OnepayTest {
    @Test
    public void testSetIntegrationApiKeyAndSharedSecret() throws Exception {
        Onepay.setIntegrationType(Onepay.IntegrationType.TEST);
        Onepay.setIntegrationApiKeyAndSharedSecret();
        Assert.assertNotNull(Onepay.getApiKey());
        Assert.assertNotNull(Onepay.getSharedSecret());
        // We will actually hit the TEST endpoint. It's the best way to test that
        // the secret and api key works :)
        Transaction.create(createCart(), Onepay.Channel.APP);
    }

    private ShoppingCart createCart() throws TransbankException {
        // Setting items to the shopping cart
        ShoppingCart cart = new ShoppingCart();
        cart.add(new Item("Zapatos", 1, 15000, null, -1));
        cart.add(new Item("Pantalon", 1, 12500, null, -1));
        return cart;
    }

}
