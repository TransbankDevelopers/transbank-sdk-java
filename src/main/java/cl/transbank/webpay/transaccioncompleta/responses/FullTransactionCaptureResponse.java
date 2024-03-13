package cl.transbank.webpay.transaccioncompleta.responses;

import lombok.*;

/**
 * This class represents a response to a capture operation for a full transaction.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class FullTransactionCaptureResponse {

  private String authorizationCode;
  private String authorizationDate;
  private double capturedAmount;
  private byte responseCode;
}
