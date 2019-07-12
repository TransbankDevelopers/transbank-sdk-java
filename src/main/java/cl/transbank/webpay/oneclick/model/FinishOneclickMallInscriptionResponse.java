package cl.transbank.webpay.oneclick.model;

import lombok.*;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter @ToString
public class FinishOneclickMallInscriptionResponse {
    private byte responseCode;
    private String tbkUser;
    private String authorizationCode;
    private String creditCardType;
    private String lastFourCardDigits;
}
