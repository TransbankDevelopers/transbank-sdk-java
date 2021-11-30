package cl.transbank.webpay.oneclick.responses;

import lombok.*;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter @ToString
public class OneclickMallInscriptionFinishResponse {
    private byte responseCode;
    private String tbkUser;
    private String authorizationCode;
    private String cardType;
    private String cardNumber;
}
