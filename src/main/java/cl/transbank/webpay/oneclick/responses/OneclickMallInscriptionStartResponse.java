package cl.transbank.webpay.oneclick.responses;

import lombok.*;

/**
 * This class represents a response to a start operation for a OneClick Mall inscription.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class OneclickMallInscriptionStartResponse {

  private String token;
  private String urlWebpay;
}
