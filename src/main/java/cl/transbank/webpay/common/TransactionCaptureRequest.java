package cl.transbank.webpay.common;

import cl.transbank.model.WebpayApiRequest;
import lombok.*;

/**
 * This class represents a request to capture a transaction.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class TransactionCaptureRequest extends WebpayApiRequest {

  private String buyOrder;
  private String authorizationCode;
  private double captureAmount;
}
