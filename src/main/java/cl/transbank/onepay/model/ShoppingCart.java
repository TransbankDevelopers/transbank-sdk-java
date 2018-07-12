package cl.transbank.onepay.model;

import cl.transbank.onepay.exception.AmountException;
import lombok.Getter;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ShoppingCart {
    @Getter private long total;
    private List<Item> items;

    public ShoppingCart() {
        super();
        this.items = new ArrayList<>();
        this.total = 0;
    }

    public List<Item> getItems() {
        return Collections.unmodifiableList(this.items); // I don't want you can modify list content by reference
    }

    public boolean add(@NonNull final Item item) throws AmountException {
        long total = this.total + item.getAmount();
        if (total < 0)
            throw new AmountException(-1, "Total amount cannot be less than zero");

        this.total = total;
        return this.items.add(item);
    }

    public boolean remove(@NonNull final Item item) throws AmountException {
        long total = this.total - item.getAmount();
        if (total < 0)
            throw new AmountException(-1, "Total amount cannot be less than zero");

        this.total = total;

        return this.items.remove(item);
    }

    public int getItemQuantity() {
        return this.items.size();
    }
}
