package cl.transbank.webpay.transaccioncompleta.model;

import cl.transbank.webpay.transaccioncompleta.requests.TransactionCommitRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.*;

/**
 * This class represents the details of a MallTransactionCommit.
 */
public class MallTransactionCommitDetails {

  private List<MallTransactionCommitDetails.Detail> detailList = new ArrayList<>();

  private MallTransactionCommitDetails() {}

  /**
   * Builds a new MallTransactionCommitDetails.
   * @return A new MallTransactionCommitDetails.
   */
  public static MallTransactionCommitDetails build() {
    return new MallTransactionCommitDetails();
  }

  /**
   * Builds a new MallTransactionCommitDetails with the provided parameters.
   * @param commerceCode The commerce code.
   * @param buyOrder The buy order.
   * @param idQueryInstallments The id query installments.
   * @param deferredPeriodIndex The deferred period index.
   * @param gracePeriod The grace period.
   * @return A new MallTransactionCommitDetails.
   */
  public static MallTransactionCommitDetails build(
    String commerceCode,
    String buyOrder,
    Long idQueryInstallments,
    Byte deferredPeriodIndex,
    boolean gracePeriod
  ) {
    return MallTransactionCommitDetails
      .build()
      .add(
        commerceCode,
        buyOrder,
        idQueryInstallments,
        deferredPeriodIndex,
        gracePeriod
      );
  }

  /**
   * Adds a new detail to the MallTransactionCommitDetails.
   * @param commerceCode The commerce code.
   * @param buyOrder The buy order.
   * @param idQueryInstallments The id query installments.
   * @param deferredPeriodIndex The deferred period index.
   * @param gracePeriod The grace period.
   * @return The MallTransactionCommitDetails with the new detail added.
   */
  public MallTransactionCommitDetails add(
    String commerceCode,
    String buyOrder,
    Long idQueryInstallments,
    Byte deferredPeriodIndex,
    boolean gracePeriod
  ) {
    detailList.add(
      new MallTransactionCommitDetails.Detail(
        commerceCode,
        buyOrder,
        idQueryInstallments,
        deferredPeriodIndex,
        gracePeriod
      )
    );
    return this;
  }

  /**
   * Removes a detail from the MallTransactionCommitDetails.
   * @param commerceCode The commerce code.
   * @param buyOrder The buy order.
   * @param idQueryInstallments The id query installments.
   * @param deferredPeriodIndex The deferred period index.
   * @param gracePeriod The grace period.
   * @return True if the detail was removed, false otherwise.
   */
  public boolean remove(
    String commerceCode,
    String buyOrder,
    Long idQueryInstallments,
    Byte deferredPeriodIndex,
    boolean gracePeriod
  ) {
    return getDetails()
      .remove(
        new MallTransactionCommitDetails.Detail(
          commerceCode,
          buyOrder,
          idQueryInstallments,
          deferredPeriodIndex,
          gracePeriod
        )
      );
  }

  /**
   * Gets the details of the MallTransactionCommitDetails.
   * @return The details of the MallTransactionCommitDetails.
   */
  public List<MallTransactionCommitDetails.Detail> getDetails() {
    return Collections.unmodifiableList(detailList);
  }

  /**
   * This class represents the details of a MallTransactionCommit.
   */
  @Data
  @EqualsAndHashCode(callSuper = true)
  @ToString(callSuper = true)
  @AllArgsConstructor
  public class Detail extends TransactionCommitRequest {

    private String commerceCode;
    private String buyOrder;

    Detail(
      String commerceCode,
      String buyOrder,
      Long idQueryInstallments,
      Byte deferredPeriodIndex,
      boolean gracePeriod
    ) {
      super(idQueryInstallments, deferredPeriodIndex, gracePeriod);
      this.commerceCode = commerceCode;
      this.buyOrder = buyOrder;
    }
  }
}
