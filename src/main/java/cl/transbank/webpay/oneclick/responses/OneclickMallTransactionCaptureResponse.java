package cl.transbank.webpay.oneclick.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * This class represents a response to a capture operation for a OneClick Mall transaction.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class OneclickMallTransactionCaptureResponse {

  private String authorizationCode;
  private String authorizationDate;
  private double capturedAmount;
  private byte responseCode;
}
