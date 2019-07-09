package cl.transbank.webpay.webpayplus.model;

import lombok.*;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@ToString
public class CreateWebpayPlusMallTransactionResponse {
    private String token;
    private String url;
}
