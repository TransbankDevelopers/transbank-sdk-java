package cl.transbank.onepay.net;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public abstract class BaseResponse {
    private String responseCode;
    private String description;
}