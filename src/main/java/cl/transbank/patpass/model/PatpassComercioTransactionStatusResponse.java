package cl.transbank.patpass.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PatpassComercioTransactionStatusResponse {
    private boolean authorized;
    private String voucherUrl;
}
