package cl.transbank.webpay.oneclick.responses;

import lombok.*;

/**
 * This class represents a response to a start operation for an inscription.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class InscriptionStartResponse {

  private String token;
  private String urlWebpay;
  private String errorMessage;
}
