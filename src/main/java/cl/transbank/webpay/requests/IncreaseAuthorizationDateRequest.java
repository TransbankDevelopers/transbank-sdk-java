package cl.transbank.webpay.requests;

import cl.transbank.model.WebpayApiRequest;
import lombok.*;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@ToString
public class IncreaseAuthorizationDateRequest extends WebpayApiRequest {
    private String commerceCode;
    private String buyOrder;
    private String authorizationCode;
}
