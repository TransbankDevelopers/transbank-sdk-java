package cl.transbank.onepay.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Item {
    private String description;
    private int quantity;
    private int amount;
    private String additionalData;
    private long expire;
}
