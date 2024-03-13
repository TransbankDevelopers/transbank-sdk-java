package cl.transbank.webpay.transaccioncompleta.responses;

import lombok.*;

/**
 * This class represents a response to a create operation for a Full Transaction.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class FullTransactionCreateResponse {

  private String token;
}
