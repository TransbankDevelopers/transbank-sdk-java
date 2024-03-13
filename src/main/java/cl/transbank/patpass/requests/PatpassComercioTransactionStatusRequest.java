package cl.transbank.patpass.requests;

import cl.transbank.model.WebpayApiRequest;
import lombok.*;

/**
 * This class represents a request to get the status of a Patpass Comercio transaction.
 */
@NoArgsConstructor
@RequiredArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PatpassComercioTransactionStatusRequest extends WebpayApiRequest {

  @NonNull
  private String token;
}
