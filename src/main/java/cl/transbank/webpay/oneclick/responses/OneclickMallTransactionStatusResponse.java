package cl.transbank.webpay.oneclick.responses;

import cl.transbank.model.CardDetail;
import java.util.List;
import lombok.*;

/**
 * This class represents the response from the transaction status request in the Oneclick Mall service.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class OneclickMallTransactionStatusResponse {

  private String buyOrder;
  private CardDetail cardDetail;
  private String accountingDate;
  private String transactionDate;
  private List<Detail> details;

  /**
   * This class represents the details of a transaction in the Oneclick Mall service.
   */
  @NoArgsConstructor
  @AllArgsConstructor
  @Getter
  @Setter
  @ToString
  public class Detail {

    private double amount;
    private String status;
    private String authorizationCode;
    private String paymentTypeCode;
    private byte responseCode;
    private byte installmentsNumber;
    private String commerceCode;
    private String buyOrder;
    private Double balance;
    private double installmentsAmount;
  }
}
