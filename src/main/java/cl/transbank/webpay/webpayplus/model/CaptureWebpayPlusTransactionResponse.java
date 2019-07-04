package cl.transbank.webpay.webpayplus.model;

import lombok.*;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@ToString
public class CaptureWebpayPlusTransactionResponse {
    private String authorizationCode;
    private String authorizationDate;
    private double capturedAmount;
    private byte responseCode;
}
