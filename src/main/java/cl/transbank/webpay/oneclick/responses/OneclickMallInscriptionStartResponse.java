package cl.transbank.webpay.oneclick.responses;

import lombok.*;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter @ToString
public class OneclickMallInscriptionStartResponse {
    private String token;
    private String urlWebpay;
}
