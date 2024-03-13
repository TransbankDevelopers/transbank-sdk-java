package cl.transbank.patpass.responses;

import lombok.*;

/**
 * This class represents a response to a create operation for a Patpass by Webpay transaction.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PatpassByWebpayTransactionCreateResponse {

  private String token;
  private String url;
}
