package cl.transbank.onepay.net;

import cl.transbank.onepay.model.TransactionCommitResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class GetTransactionNumberResponse extends BaseResponse {
    private TransactionCommitResponse result;
}
