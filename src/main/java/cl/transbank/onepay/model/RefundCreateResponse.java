package cl.transbank.onepay.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class RefundCreateResponse {
    private String occ;
    private String externalUniqueNumber;
    private String reverseCode;
    private long issuedAt;
    private String signature;
}
