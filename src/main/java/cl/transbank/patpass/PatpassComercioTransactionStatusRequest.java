package cl.transbank.patpass;

import cl.transbank.model.WebpayApiRequest;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@NoArgsConstructor
@RequiredArgsConstructor
@Data
public class PatpassComercioTransactionStatusRequest extends WebpayApiRequest {
    @SerializedName("token") @NonNull private String token;
}
