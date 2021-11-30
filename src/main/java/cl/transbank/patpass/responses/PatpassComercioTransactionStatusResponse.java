package cl.transbank.patpass.responses;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PatpassComercioTransactionStatusResponse {
    private boolean authorized;
    @SerializedName("voucherUrl") private String voucherUrl;
}
