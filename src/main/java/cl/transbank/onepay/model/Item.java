package cl.transbank.onepay.model;

import lombok.*;
import lombok.experimental.Accessors;

import java.util.UUID;

@ToString
public class Item implements Cloneable {
    @Getter private final transient String id;
    @Getter @Setter @Accessors(chain = true) private String description;
    @Getter @Setter @Accessors(chain = true) private int quantity;
    @Getter @Setter @Accessors(chain = true) private int amount;
    @Getter @Setter @Accessors(chain = true) String additionalData;
    @Getter @Setter @Accessors(chain = true) private long expire;

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
