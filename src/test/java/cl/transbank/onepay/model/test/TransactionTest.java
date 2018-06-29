package cl.transbank.onepay.model.test;

import cl.transbank.onepay.Onepay;
import cl.transbank.onepay.exception.AmountException;
import cl.transbank.onepay.model.CreateTransactionResponse;
import cl.transbank.onepay.model.Item;
import cl.transbank.onepay.model.ShoppingCart;
import cl.transbank.onepay.model.Transaction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TransactionTest {
    public void testSendTransaction() throws AmountException, IOException {
        // Setting comerce data
        Onepay.sharedSecret = "P4DCPS55QB2QLT56SQH6#W#LV76IAPYX";
        Onepay.apiKey = "mUc0GxYGor6X8u-_oB3e-HWJulRG01WoC96-_tUA3Bg";
        Onepay.appKey = "04533c31-fe7e-43ed-bbc4-1c8ab1538afp";
        Onepay.callbackUrl = "http://localhost:8080/ewallet-endpoints";

        // Setting items to the shopping cart
        List<Item> items = new ArrayList<Item>();
        items.add(new Item("Piece of shit", 1, 15000, null, -1));
        items.add(new Item("Another piece of shit", 1, 15000, null, -1));
        ShoppingCart cart = new ShoppingCart();
        cart.add(new Item("Piece of shit", 1, 15000, null, -1));
        cart.add(new Item("Another piece of shit", 1, 12500, null, -1));

        // Send transaction to Transbank
        CreateTransactionResponse response = Transaction.create(cart);

        // Print response
        System.out.println(response);

        assert null != response && response.getResponseCode().equalsIgnoreCase("ok")
                && null != response.getResult() && null != response.getResult().getQrCodeAsBase64();
    }
}
