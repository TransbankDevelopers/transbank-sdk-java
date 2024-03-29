package cl.transbank.patpass.responses;

import cl.transbank.model.CardDetail;
import lombok.*;

/**
 * This class represents a response to a status request for a Patpass by Webpay transaction.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PatpassByWebpayTransactionStatusResponse {

  private String vci;
  private double amount;
  private String status;
  private double balance;
  private String buyOrder;
  private String sessionId;
  private CardDetail cardDetail;
  private String accountingDate;
  private String transactionDate;
  private String authorizationCode;
  private String paymentTypeCode;
  private byte installmentsNumber;
}
