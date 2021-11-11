package cl.transbank.webpay.common;

import cl.transbank.model.WebpayApiRequest;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MallTransactionRefundRequest extends WebpayApiRequest {
    private String buyOrder;
    private String commerceCode;
    private double amount;
}
