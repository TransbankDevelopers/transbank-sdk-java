package cl.transbank.webpay.transaccioncompleta.responses;

import lombok.*;

/**
 * This class represents the response of a refund operation in a Full Transaction.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class FullTransactionRefundResponse {

  private String type;
  private String authorizationCode;
  private String authorizationDate;
  private double nullifiedAmount;
  private double balance;
  private byte responseCode;
  private Double prepaidBalance;
}
