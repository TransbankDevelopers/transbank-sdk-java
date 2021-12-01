package cl.transbank.webpay.transaccioncompleta.requests;

import cl.transbank.model.MallTransactionCreateDetails;
import cl.transbank.model.WebpayApiRequest;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MallFullTransactionCreateRequest extends WebpayApiRequest {
    private String buyOrder;
    private String sessionId;
    private String cardNumber;
    private String cardExpirationDate;
    private List<MallTransactionCreateDetails.Detail> details;
    private Short cvv;
}
