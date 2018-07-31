package cl.transbank.onepay.model.test;

import cl.transbank.onepay.exception.AmountException;
import cl.transbank.onepay.model.Item;
import cl.transbank.onepay.model.ShoppingCart;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ShoppingCartTest {
    private Item itemOne = new Item("item one", 2, 1000, null, -1);
    private Item itemTwo =new Item("item two", 4, 1000, null, -1);
    private Item itemThree =new Item("item three", 1, 1000, null, -1);

    @Test
    public void testTotalAmount() throws AmountException {
        ShoppingCart cart = new ShoppingCart();
        cart.add(itemOne);
        cart.add(itemTwo);

        assertEquals(6000, cart.getTotal());
    }

    @Test
    public void testItemsQuantity() throws AmountException {
        ShoppingCart cart = new ShoppingCart();
        cart.add(itemOne);
        cart.add(itemTwo);

        assertEquals(6, cart.getItemsQuantity());
    }

    @Test
    public void testImmutableList() throws AmountException {
        ShoppingCart cart = new ShoppingCart();
        cart.add(itemOne);
        cart.add(itemTwo);

        final List<Item> items = cart.getItems();

        try {
            items.add(itemThree);
        } catch (UnsupportedOperationException e) {}

        assertEquals(2, cart.getItems().size());

        final Item item = items.get(0);
        final int quantity = item.getQuantity();
        item.setQuantity(10);

        Item original = null;
        for (Item itm : cart.getItems()) {
            if (itm.getId().equals(item.getId())) {
                original = itm;
                break;
            }
        }

        assertNotNull(original);

        assertEquals(quantity, original.getQuantity());
    }

    @Test
    public void testItemRemove() throws AmountException {
        ShoppingCart cart = new ShoppingCart();
        cart.add(itemOne);
        cart.add(itemTwo);

        final boolean itemOneRemoved = cart.remove(itemOne);
        assertTrue(itemOneRemoved);

        final boolean itemThreeRemoved = cart.remove(itemThree);
        assertFalse("This item could not be removed from cart because it does not exist", itemThreeRemoved);

        final long total = cart.getTotal();
        assertEquals(4000, total);

        final boolean removeAgain = cart.remove(itemOne);
        assertFalse("This item has already been removed", removeAgain);

        itemTwo.setQuantity(3);
        cart.remove(itemTwo);
        final long newTotal = cart.getTotal();
        assertEquals(0, newTotal);
    }
}
