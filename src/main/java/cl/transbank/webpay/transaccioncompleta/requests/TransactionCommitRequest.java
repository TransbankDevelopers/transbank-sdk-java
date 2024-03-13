package cl.transbank.webpay.transaccioncompleta.requests;

import cl.transbank.model.WebpayApiRequest;
import lombok.*;

/**
 * This class represents a request to commit a transaction.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class TransactionCommitRequest extends WebpayApiRequest {

  private Long idQueryInstallments;
  private Byte deferredPeriodIndex;
  private Boolean gracePeriod;
}
