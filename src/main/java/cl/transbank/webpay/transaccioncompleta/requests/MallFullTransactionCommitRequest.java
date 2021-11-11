package cl.transbank.webpay.transaccioncompleta.requests;

import cl.transbank.model.WebpayApiRequest;
import cl.transbank.webpay.transaccioncompleta.model.MallTransactionCommitDetails;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class MallFullTransactionCommitRequest extends WebpayApiRequest {
    private List<MallTransactionCommitDetails.Detail> details;

}
