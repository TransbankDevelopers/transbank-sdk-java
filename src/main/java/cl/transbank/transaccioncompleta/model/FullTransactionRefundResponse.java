package cl.transbank.transaccioncompleta.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class FullTransactionRefundResponse {
    private String type;
    private String authorizationCode;
    private String authorizationDate;
    private double nullifiedAmount;
    private double balance;
    private byte responseCode;


}
