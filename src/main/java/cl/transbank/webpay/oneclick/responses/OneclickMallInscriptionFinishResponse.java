package cl.transbank.webpay.oneclick.responses;

import lombok.*;

/**
 * This class represents a response to a finish operation for a OneClick Mall inscription.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class OneclickMallInscriptionFinishResponse {

  private byte responseCode;
  private String tbkUser;
  private String authorizationCode;
  private String cardType;
  private String cardNumber;
}
