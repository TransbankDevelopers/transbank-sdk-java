package cl.transbank.onepay.net;

import cl.transbank.onepay.model.TransactionCreateResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class SendTransactionResponse extends BaseResponse {
    private TransactionCreateResponse result;
}
