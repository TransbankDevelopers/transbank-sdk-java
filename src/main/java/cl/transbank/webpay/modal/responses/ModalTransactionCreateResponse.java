package cl.transbank.webpay.modal.responses;

import lombok.*;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@ToString
public class ModalTransactionCreateResponse {
    private String token;
}
