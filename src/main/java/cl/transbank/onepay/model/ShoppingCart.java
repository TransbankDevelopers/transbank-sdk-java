package cl.transbank.onepay.model;

import cl.transbank.onepay.exception.AmountException;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.util.*;

@ToString
public final class ShoppingCart {
    @Getter private long total;
    @Getter private int itemsQuantity;
    private Map<String, Item> itemMap;

    public ShoppingCart() {
        super();
        this.itemMap = new HashMap<>();
        this.total = 0;
        this.itemsQuantity = 0;
    }

    public List<Item> getItems() {
        List<Item> copy = new ArrayList<>();

        for (Item item : itemMap.values()) {
            copy.add(item.clone());
        }

        return copy;
    }

    public boolean add(@NonNull final Item item) throws AmountException {
        try {
            this.itemMap.put(item.getId(), item.clone());
        } catch (Exception e) {
            return false;
        }

        synchronized (ShoppingCart.class) {
            long total = this.total + (item.getAmount() * item.getQuantity());
            if (total < 0)
                throw new AmountException("Total amount cannot be less than zero");

            this.total = total;
            this.itemsQuantity += item.getQuantity();
        }

        return true;
    }

    public boolean remove(@NonNull final Item item) throws AmountException {
        Item remove;

        try {
            remove = this.itemMap.remove(item.getId());
            if (null == remove)
                return false;
        } catch (Exception e) {
            return false;
        }

        synchronized (ShoppingCart.class) {
            long total = this.total - (remove.getAmount() * remove.getQuantity());
            if (total < 0)
                throw new AmountException("Total amount cannot be less than zero");

            this.total = total;
            this.itemsQuantity -= remove.getQuantity();
        }

        return true;
    }
}
