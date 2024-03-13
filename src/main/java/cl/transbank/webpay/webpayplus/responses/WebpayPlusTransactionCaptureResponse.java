package cl.transbank.webpay.webpayplus.responses;

import lombok.*;

/**
 * This class represents a response to a capture operation for a Webpay Plus transaction.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class WebpayPlusTransactionCaptureResponse {

  private String authorizationCode;
  private String authorizationDate;
  private double capturedAmount;
  private byte responseCode;
}
