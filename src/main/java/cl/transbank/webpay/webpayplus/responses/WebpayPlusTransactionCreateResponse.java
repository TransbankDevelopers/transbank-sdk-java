package cl.transbank.webpay.webpayplus.responses;

import lombok.*;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@ToString
public class WebpayPlusTransactionCreateResponse {
    private String token;
    private String url;
}
