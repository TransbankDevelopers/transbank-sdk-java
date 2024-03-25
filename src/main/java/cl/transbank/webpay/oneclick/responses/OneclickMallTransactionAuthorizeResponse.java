package cl.transbank.webpay.oneclick.responses;

import cl.transbank.model.CardDetail;
import java.util.List;
import lombok.*;

/**
 * This class represents the response from the OneclickMallTransaction authorization request.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class OneclickMallTransactionAuthorizeResponse {

  private String transactionDate;
  private String accountingDate;
  private CardDetail cardDetail;
  private String buyOrder;
  private List<Detail> details;

  /**
   * This class represents the details of the OneclickMallTransaction authorization response.
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
    private byte installmentsNumber;
    private String commerceCode;
    private String buyOrder;
    private byte responseCode;
    private double installmentsAmount;
  }
}
