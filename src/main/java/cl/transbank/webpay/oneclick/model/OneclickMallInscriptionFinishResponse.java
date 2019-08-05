package cl.transbank.webpay.oneclick.model;

import lombok.*;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter @ToString
public class OneclickMallInscriptionFinishResponse {
    private byte responseCode;
    private String tbkUser;
    private String authorizationCode;
    private String creditCardType;
    private String lastFourCardDigits;
}
