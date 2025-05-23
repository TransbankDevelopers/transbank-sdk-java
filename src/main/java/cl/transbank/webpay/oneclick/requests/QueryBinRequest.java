package cl.transbank.webpay.oneclick.requests;

import cl.transbank.model.WebpayApiRequest;
import lombok.*;

/**
 * This class represents a request to authorize a transaction.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class QueryBinRequest extends WebpayApiRequest {
    private String username;
}
