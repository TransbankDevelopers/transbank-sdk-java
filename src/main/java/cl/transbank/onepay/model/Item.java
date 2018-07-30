package cl.transbank.onepay.model;

import lombok.*;

import java.util.UUID;

@ToString
public class Item implements Cloneable {
    @Getter private final String id;
    @Getter @Setter private String description;
    @Getter @Setter private int quantity;
    @Getter @Setter private int amount;
    @Getter @Setter private String additionalData;
    @Getter @Setter private long expire;

    public Item() {
        super();
        this.id = UUID.randomUUID().toString();
    }

    public Item(String description, int quantity, int amount, String additionalData, long expire) {
        this();
        this.description = description;
        this.quantity = quantity;
        this.amount = amount;
        this.additionalData = additionalData;
        this.expire = expire;
    }

    public Item clone() {
        try {
            return (Item) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
}
