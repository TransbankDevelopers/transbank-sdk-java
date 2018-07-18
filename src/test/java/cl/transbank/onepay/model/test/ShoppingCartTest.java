package cl.transbank.onepay.model.test;

import cl.transbank.onepay.exception.AmountException;
import cl.transbank.onepay.model.Item;
import cl.transbank.onepay.model.ShoppingCart;

public class ShoppingCartTest {
    private Item itemOne = new Item("item one", 2, 1000, null, -1);
    private Item itemTwo =new Item("item two", 4, 1000, null, -1);

    public void testTotalAmount() throws AmountException {
        ShoppingCart cart = new ShoppingCart();
        cart.add(itemOne);
        cart.add(itemTwo);

        assert cart.getTotal() == 6000;
    }

    public void testItemsQuantity() throws AmountException {
        ShoppingCart cart = new ShoppingCart();
        cart.add(itemOne);
        cart.add(itemTwo);

        assert cart.getItemsQuantity() == 6;
    }
}
