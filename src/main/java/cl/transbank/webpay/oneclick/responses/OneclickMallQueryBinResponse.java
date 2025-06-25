package cl.transbank.webpay.oneclick.responses;

import lombok.*;

/**
 * This class represents the response from the OneclickMallBinInfo
 * query bin request.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class OneclickMallQueryBinResponse {
    private String binIssuer;
    private String binPaymentType;
    private String binBrand;
}
