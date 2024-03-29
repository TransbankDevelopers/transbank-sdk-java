package cl.transbank.webpay.transaccioncompleta.requests;

import cl.transbank.model.WebpayApiRequest;
import lombok.*;

/**
 * This class represents a request to get the installments information for a Mall Full Transaction.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MallFullTransactionInstallmentsRequest extends WebpayApiRequest {

  private String commerceCode;
  private String buyOrder;
  private byte installmentsNumber;
}
