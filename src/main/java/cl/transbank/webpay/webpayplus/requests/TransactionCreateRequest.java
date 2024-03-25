package cl.transbank.webpay.webpayplus.requests;

import cl.transbank.model.WebpayApiRequest;
import lombok.*;

/**
 * This class represents a request to create a transaction.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class TransactionCreateRequest extends WebpayApiRequest {

  /**
   * The buy order of the transaction.
   */
  private String buyOrder;

  /**
   * The session ID of the transaction.
   */
  private String sessionId;

  /**
   * The amount of the transaction.
   */
  private double amount;

  /**
   * The return URL of the transaction.
   */
  private String returnUrl;
}
