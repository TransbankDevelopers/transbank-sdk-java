package cl.transbank.webpay.responses;

import lombok.*;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@ToString
public class DeferredCaptureHistoryResponse {
    private String type;
    private double amount;
    private String authorizationCode;
    private String authorizationDate;
    private double totalAmount;
    private String expirationDate;
    private byte responseCode;
}
