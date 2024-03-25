package cl.transbank.webpay.oneclick.requests;

import cl.transbank.model.WebpayApiRequest;
import lombok.*;

/**
 * This class represents a request to refund a transaction for a mall.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MallTransactionRefundRequest extends WebpayApiRequest {

  private String commerceCode;
  private String detailBuyOrder;
  private double amount;
}
