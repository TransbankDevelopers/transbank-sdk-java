package cl.transbank.webpay.transaccioncompleta.responses;

import cl.transbank.model.CardDetail;
import java.util.List;
import lombok.*;

/**
 * This class represents the response from the MallFullTransaction status request.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MallFullTransactionStatusResponse {

  private String buyOrder;
  private String sessionId;
  private String cardNumber;
  private String expirationDate;
  private String accountingDate;
  private String transactionDate;
  private List<Detail> details;
  private Double prepaidBalance;
  private CardDetail cardDetail;

  /**
   * This class represents the details of the MallFullTransaction status response.
   */
  @NoArgsConstructor
  @AllArgsConstructor
  @Data
  public class Detail {

    private String authorizationCode;
    private String paymentTypeCode;
    private byte responseCode;
    private double installmentsAmount;
    private byte installmentsNumber;
    private double amount;
    private String commerceCode;
    private String buyOrder;
    private String status;
    private double balance;
  }
}
