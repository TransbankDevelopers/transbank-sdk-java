package cl.transbank.webpay.common;

import cl.transbank.model.WebpayApiRequest;
import lombok.*;

/**
 * This class represents a request to refund a transaction.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class TransactionRefundRequest extends WebpayApiRequest {

  private double amount;
}
