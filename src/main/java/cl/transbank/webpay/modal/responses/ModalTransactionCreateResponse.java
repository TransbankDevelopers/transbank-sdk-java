package cl.transbank.webpay.modal.responses;

import lombok.*;

/**
 * This class represents a response to a create operation for a Modal transaction.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ModalTransactionCreateResponse {

  private String token;
}
