package cl.transbank.patpass;

import cl.transbank.model.WebpayApiRequest;
import com.google.gson.annotations.SerializedName;
import lombok.*;

@NoArgsConstructor
@RequiredArgsConstructor
@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class PatpassComercioTransactionStatusRequest extends WebpayApiRequest {
    @SerializedName("token") @NonNull private String token;
}
