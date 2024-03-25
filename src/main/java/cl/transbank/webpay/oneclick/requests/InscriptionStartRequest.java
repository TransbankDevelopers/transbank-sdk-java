package cl.transbank.webpay.oneclick.requests;

import cl.transbank.model.WebpayApiRequest;
import lombok.*;

/**
 * This class represents a request to start an inscription.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class InscriptionStartRequest extends WebpayApiRequest {

  /**
   * The username for the inscription.
   */
  private String username;

  /**
   * The email for the inscription.
   */
  private String email;

  /**
   * The response URL for the inscription.
   */
  private String responseUrl;
}
