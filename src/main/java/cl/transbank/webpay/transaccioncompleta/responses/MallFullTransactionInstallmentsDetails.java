package cl.transbank.webpay.transaccioncompleta.responses;

import cl.transbank.webpay.transaccioncompleta.requests.MallFullTransactionInstallmentsRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class represents the details of a MallFullTransactionInstallments.
 */
public class MallFullTransactionInstallmentsDetails {

  private List<MallFullTransactionInstallmentsDetails.Detail> detailList = new ArrayList<>();

  /**
   * Private constructor. Prevents direct instantiation.
   */
  private MallFullTransactionInstallmentsDetails() {}

  /**
   * Builds a new MallFullTransactionInstallmentsDetails.
   * @return A new MallFullTransactionInstallmentsDetails.
   */
  public static MallFullTransactionInstallmentsDetails build() {
    return new MallFullTransactionInstallmentsDetails();
  }

  /**
   * Builds a new MallFullTransactionInstallmentsDetails with the provided parameters.
   * @param commerceCode The commerce code.
   * @param buyOrder The buy order.
   * @param installmentsNumber The number of installments.
   * @return A new MallFullTransactionInstallmentsDetails.
   */
  public static MallFullTransactionInstallmentsDetails build(
    String commerceCode,
    String buyOrder,
    byte installmentsNumber
  ) {
    return MallFullTransactionInstallmentsDetails
      .build()
      .add(commerceCode, buyOrder, installmentsNumber);
  }

  /**
   * Adds a new detail to the MallFullTransactionInstallmentsDetails.
   * @param commerceCode The commerce code.
   * @param buyOrder The buy order.
   * @param installmentsNumber The number of installments.
   * @return The MallFullTransactionInstallmentsDetails with the new detail added.
   */
  public MallFullTransactionInstallmentsDetails add(
    String commerceCode,
    String buyOrder,
    byte installmentsNumber
  ) {
    detailList.add(
      new MallFullTransactionInstallmentsDetails.Detail(
        commerceCode,
        buyOrder,
        installmentsNumber
      )
    );
    return this;
  }

  /**
   * Removes a detail from the MallFullTransactionInstallmentsDetails.
   * @param commerceCode The commerce code.
   * @param buyOrder The buy order.
   * @param installmentsNumber The number of installments.
   * @return True if the detail was removed, false otherwise.
   */
  public boolean remove(
    String commerceCode,
    String buyOrder,
    byte installmentsNumber
  ) {
    return getDetails()
      .remove(
        new MallFullTransactionInstallmentsDetails.Detail(
          commerceCode,
          buyOrder,
          installmentsNumber
        )
      );
  }

  /**
   * Gets the details of the MallFullTransactionInstallmentsDetails.
   * @return The details of the MallFullTransactionInstallmentsDetails.
   */
  public List<MallFullTransactionInstallmentsDetails.Detail> getDetails() {
    return Collections.unmodifiableList(detailList);
  }

  /**
   * This class represents the detail of a MallFullTransactionInstallments.
   */
  public class Detail extends MallFullTransactionInstallmentsRequest {

    /**
     * Constructor with parameters.
     * @param commerceCode The commerce code.
     * @param buyOrder The buy order.
     * @param installmentsNumber The number of installments.
     */
    Detail(String commerceCode, String buyOrder, byte installmentsNumber) {
      super(commerceCode, buyOrder, installmentsNumber);
    }
  }
}
