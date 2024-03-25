package cl.transbank.webpay.common;

import cl.transbank.model.WebpayApiRequest;
import lombok.*;

/**
 * This class represents a request to refund a Mall transaction.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MallTransactionRefundRequest extends WebpayApiRequest {

  private String buyOrder;
  private String commerceCode;
  private double amount;
}
