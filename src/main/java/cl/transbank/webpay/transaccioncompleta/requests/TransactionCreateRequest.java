package cl.transbank.webpay.transaccioncompleta.requests;

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

  private String buyOrder;
  private String sessionId;
  private double amount;
  private String cardNumber;
  private short cvv;
  private String cardExpirationDate;
}
