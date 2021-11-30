package cl.transbank.webpay.oneclick.requests;

import cl.transbank.model.WebpayApiRequest;
import lombok.*;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter @ToString
public class MallTransactionRefundRequest extends WebpayApiRequest {
    private String commerceCode;
    private String detailBuyOrder;
    private double amount;
}
