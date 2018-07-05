package cl.transbank.onepay.model.test;

import cl.transbank.onepay.Onepay;
import cl.transbank.onepay.exception.AmountException;
import cl.transbank.onepay.model.*;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class TransactionTest {
    public void testSendTransactionOneWay() throws AmountException, IOException, InvalidKeyException, NoSuchAlgorithmException {
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

        // Print response
        System.out.println(response);

        assert null != response && response.getResponseCode().equalsIgnoreCase("ok")
                && null != response.getResult() && null != response.getResult().getQrCodeAsBase64();
    }

    public void testSendTransactionSecondWay() throws AmountException, IOException, NoSuchAlgorithmException, InvalidKeyException {
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

        // Print response
        System.out.println(response);

        assert null != response && response.getResponseCode().equalsIgnoreCase("ok")
                && null != response.getResult() && null != response.getResult().getQrCodeAsBase64();
    }
}
