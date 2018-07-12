package cl.transbank.onepay.net;

import cl.transbank.onepay.model.RefundCreateResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class NullifyTransactionResponse extends BaseResponse {
    private RefundCreateResponse result;
}
