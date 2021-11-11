package cl.transbank.webpay.transaccioncompleta.responses;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class FullTransactionCreateResponse {
    private String token;
}
