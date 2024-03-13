package cl.transbank.model;

import lombok.*;

/**
 * This class represents a base response for a refund operation.
 * It is abstract and should be extended by specific refund response classes.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public abstract class BaseRefundResponse {

  private String type;
  private double balance;
  private String authorizationCode;
  private byte responseCode;
  private String authorizationDate;
  private double nullifiedAmount;
  private Double prepaidBalance;
}
