package cl.transbank.patpass.responses;

import cl.transbank.model.CardDetail;
import lombok.*;

/**
 * This class represents a response to a commit operation for a Patpass By Webpay transaction.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PatpassByWebpayTransactionCommitResponse {

  private String vci;
  private double amount;
  private String status;
  private String buyOrder;
  private String sessionId;
  private CardDetail cardDetail;
  private String accountingDate;
  private String transactionDate;
  private String authorizationCode;
  private String paymentTypeCode;
  private byte responseCode;
  private byte installmentsNumber;
}
