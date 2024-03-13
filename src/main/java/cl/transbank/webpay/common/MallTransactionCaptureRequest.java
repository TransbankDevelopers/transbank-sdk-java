package cl.transbank.webpay.common;

import cl.transbank.model.WebpayApiRequest;
import lombok.*;

/**
 * This class represents a request to capture a mall transaction.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MallTransactionCaptureRequest extends WebpayApiRequest {

  private String commerceCode;
  private String buyOrder;
  private String authorizationCode;
  private double captureAmount;
}
