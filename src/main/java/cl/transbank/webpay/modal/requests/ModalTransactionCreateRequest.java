package cl.transbank.webpay.modal.requests;

import cl.transbank.model.WebpayApiRequest;
import lombok.*;

/**
 * This class represents a request to create a modal transaction.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ModalTransactionCreateRequest extends WebpayApiRequest {

  private String buyOrder;
  private String sessionId;
  private double amount;
}
