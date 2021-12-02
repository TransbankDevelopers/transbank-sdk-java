package cl.transbank.webpay.webpayplus.requests;

import cl.transbank.model.WebpayApiRequest;
import lombok.*;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@ToString
public class ModalTransactionCreateRequest extends WebpayApiRequest {
    private String buyOrder;
    private String sessionId;
    private double amount;
}