package cl.transbank.onepay.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class TransactionCreateResponse {
    private String occ;
    private long ott;
    private String externalUniqueNumber;
    private String qrCodeAsBase64;
    private long issuedAt;
    private String signature;
}
