package cl.transbank.webpay.webpayplus.responses;

import lombok.*;

/**
 * This class represents a response to a create operation for a Webpay Plus transaction.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class WebpayPlusTransactionCreateResponse {

  private String token;
  private String url;
}
