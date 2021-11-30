package cl.transbank.webpay.common;

import cl.transbank.model.WebpayApiRequest;
import lombok.*;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@ToString
public class MallTransactionCaptureRequest extends WebpayApiRequest {
    private String commerceCode;
    private String buyOrder;
    private String authorizationCode;
    private double captureAmount;
}
