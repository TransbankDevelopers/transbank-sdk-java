package cl.transbank.webpay.oneclick.requests;

import cl.transbank.model.WebpayApiRequest;
import lombok.*;

/**
 * This class represents a request to delete an inscription.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class InscriptionDeleteRequest extends WebpayApiRequest {

  private String username;
  private String tbkUser;
}
