package cl.transbank.onepay.model;

import cl.transbank.onepay.net.BaseResponse;
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
}
