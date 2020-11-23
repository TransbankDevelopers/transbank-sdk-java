package cl.transbank.webpay.oneclick.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter @ToString
public class OneclickMallTransactionCaptureResponse {
    private String authorizationCode;
    private String authorizationDate;
    private double capturedAmount;
    private byte responseCode;
}
