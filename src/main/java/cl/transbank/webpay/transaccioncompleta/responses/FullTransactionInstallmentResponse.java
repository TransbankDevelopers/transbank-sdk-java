package cl.transbank.webpay.transaccioncompleta.responses;

import java.util.List;
import lombok.*;

/**
 * This class represents the details of the OneclickMallTransaction authorization response.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class FullTransactionInstallmentResponse {

  private double installmentsAmount;
  private Long idQueryInstallments;
  private List<DeferredPeriod> deferredPeriods;

  /**
   * This class represents the details of the FullTransaction installment response.
   */
  @NoArgsConstructor
  @AllArgsConstructor
  @Getter
  @Setter
  @ToString
  public class DeferredPeriod {

    private double amount;
    private byte period;
  }
}
