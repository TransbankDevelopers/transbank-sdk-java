package cl.transbank.webpay.transaccioncompleta.responses;

import java.util.List;
import lombok.NoArgsConstructor;

/**
 * This class represents the response of an installment operation in a Mall Full Transaction.
 */
@NoArgsConstructor
public class MallFullTransactionInstallmentResponse
  extends FullTransactionInstallmentResponse {

  /**
   * Constructs a new MallFullTransactionInstallmentResponse with the specified parameters.
   * @param installmentsAmount The amount of the installments.
   * @param idQueryInstallments The ID of the query installments.
   * @param deferredPeriods The list of deferred periods.
   */
  MallFullTransactionInstallmentResponse(
    double installmentsAmount,
    Long idQueryInstallments,
    List<DeferredPeriod> deferredPeriods
  ) {
    super(installmentsAmount, idQueryInstallments, deferredPeriods);
  }
}
