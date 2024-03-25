package cl.transbank.webpay.transaccioncompleta.requests;

import cl.transbank.model.WebpayApiRequest;
import cl.transbank.webpay.transaccioncompleta.model.MallTransactionCommitDetails;
import java.util.List;
import lombok.*;

/**
 * This class represents a request to commit a full transaction in the mall.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MallFullTransactionCommitRequest extends WebpayApiRequest {

  private List<MallTransactionCommitDetails.Detail> details;
}
