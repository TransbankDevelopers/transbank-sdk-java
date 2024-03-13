package cl.transbank.webpay.webpayplus.requests;

import cl.transbank.model.MallTransactionCreateDetails;
import cl.transbank.model.WebpayApiRequest;
import java.util.List;
import lombok.*;

/**
 * This class represents a request to create a Mall Transaction.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MallTransactionCreateRequest extends WebpayApiRequest {

  private String buyOrder;
  private String sessionId;
  private String returnUrl;
  private List<MallTransactionCreateDetails.Detail> details;
}
