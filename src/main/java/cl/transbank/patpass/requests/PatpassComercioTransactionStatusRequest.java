package cl.transbank.patpass.requests;

import cl.transbank.model.WebpayApiRequest;
import lombok.*;

@NoArgsConstructor
@RequiredArgsConstructor
@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class PatpassComercioTransactionStatusRequest extends WebpayApiRequest {
    @NonNull private String token;
}
