package cl.transbank.webpay.webpayplus.responses;

import lombok.*;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@ToString
public class ModalTransactionCreateResponse {
    private String token;
}
