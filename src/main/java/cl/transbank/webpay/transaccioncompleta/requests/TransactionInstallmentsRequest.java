package cl.transbank.webpay.transaccioncompleta.requests;

import cl.transbank.model.WebpayApiRequest;
import lombok.*;

/**
 * This class represents a request to get the installments information for a Transaccion Completa transaction.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class TransactionInstallmentsRequest extends WebpayApiRequest {

  private byte installmentsNumber;
}
