package cl.transbank.onepay.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class RefundCreateResponse extends BaseResponse {
    private Result result;

    @Getter
    @Setter
    @ToString
    public class Result {

    }
}
