package cl.transbank.onepay.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public abstract class BaseResponse {
    private String sdkMessage;
    private String responseCode;
    private String description;
}