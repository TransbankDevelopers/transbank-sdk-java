package cl.transbank.model;

import lombok.*;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter @ToString
public abstract class BaseRefundResponse {
    private String type;
    private double balance;
    private String authorizationCode;
    private byte responseCode;
    private String authorizationDate;
    private double nullifiedAmount;
    private Double prepaidBalance;
}
