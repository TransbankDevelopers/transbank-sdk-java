package cl.transbank.transaccioncompleta.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@ToString
public class FullTransactionCaptureResponse {
    private String authorizationCode;
    private String authorizationDate;
    private double capturedAmount;
    private byte responseCode;
}
