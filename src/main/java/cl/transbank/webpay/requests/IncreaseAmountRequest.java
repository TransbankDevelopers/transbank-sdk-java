package cl.transbank.webpay.requests;

import cl.transbank.model.WebpayApiRequest;
import lombok.*;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@ToString
public class IncreaseAmountRequest extends WebpayApiRequest {

    private String commerceCode;
    private String buyOrder;
    private String authorizationCode;
    private double amount;

}
