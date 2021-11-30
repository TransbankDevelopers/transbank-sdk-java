package cl.transbank.webpay.oneclick.requests;

import cl.transbank.model.WebpayApiRequest;
import cl.transbank.webpay.oneclick.model.MallTransactionCreateDetails;
import lombok.*;

import java.util.List;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter @ToString
public class TransactionAuthorizeRequest extends WebpayApiRequest {
    private String username;
    private String tbkUser;
    private String buyOrder;
    private List<MallTransactionCreateDetails.Detail> details;
}
