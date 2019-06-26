package cl.transbank.webpay.webpayplus.model;

import lombok.*;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@ToString
public class RefundWebpayPlusTransactionResponse {
    private String type;
    private double balance;
    private String authorizationCode;
    private byte responseCode;
    private String authorizationDate;
    private double nullifiedAmount;
}
