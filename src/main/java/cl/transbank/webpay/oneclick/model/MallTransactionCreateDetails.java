package cl.transbank.webpay.oneclick.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.*;

/**
 * This class represents the details for creating a mall transaction.
 */
@ToString
public class MallTransactionCreateDetails {

  @Getter
  private List<Detail> details = new ArrayList<>();

  private MallTransactionCreateDetails() {}

  /**
   * Builds a new MallTransactionCreateDetails.
   * @return A new MallTransactionCreateDetails.
   */
  public static MallTransactionCreateDetails build() {
    return new MallTransactionCreateDetails();
  }

  /**
   * Builds a new MallTransactionCreateDetails with the specified amount, commerce code, buy order, and installments number.
   * @param amount The amount of the transaction.
   * @param commerceCode The commerce code of the transaction.
   * @param buyOrder The buy order of the transaction.
   * @param installmentsNumber The number of installments for the transaction.
   * @return A new MallTransactionCreateDetails.
   */
  public static MallTransactionCreateDetails build(
    double amount,
    String commerceCode,
    String buyOrder,
    byte installmentsNumber
  ) {
    return MallTransactionCreateDetails
      .build()
      .add(amount, commerceCode, buyOrder, installmentsNumber);
  }

  /**
   * Adds a new detail to the details of the transaction.
   * @param amount The amount of the detail.
   * @param commerceCode The commerce code of the detail.
   * @param buyOrder The buy order of the detail.
   * @param installmentsNumber The number of installments for the detail.
   * @return This MallTransactionCreateDetails.
   */
  public MallTransactionCreateDetails add(
    double amount,
    String commerceCode,
    String buyOrder,
    byte installmentsNumber
  ) {
    getDetails()
      .add(new Detail(amount, commerceCode, buyOrder, installmentsNumber));
    return this;
  }

  /**
   * Removes a detail from the details of the transaction.
   * @param amount The amount of the detail.
   * @param commerceCode The commerce code of the detail.
   * @param buyOrder The buy order of the detail.
   * @param installmentsNumber The number of installments for the detail.
   * @return True if the detail was removed, false otherwise.
   */
  public boolean remove(
    double amount,
    String commerceCode,
    String buyOrder,
    byte installmentsNumber
  ) {
    return getDetails()
      .remove(new Detail(amount, commerceCode, buyOrder, installmentsNumber));
  }

  /**
   * This class represents a detail of a mall transaction.
   */
  @NoArgsConstructor
  @AllArgsConstructor
  @Getter
  @Setter
  @ToString
  public class Detail {

    private double amount;
    private String commerceCode;
    private String buyOrder;
    private byte installmentsNumber;

    @Override
    public boolean equals(Object obj) {
      if (!(obj instanceof Detail)) return false;

      Detail another = (Detail) obj;
      return (
        getAmount() == another.getAmount() &&
        getCommerceCode().equals(another.getBuyOrder()) &&
        getBuyOrder().equals(another.getBuyOrder()) &&
        getInstallmentsNumber() == another.getInstallmentsNumber()
      );
    }

    @Override
    public int hashCode() {
      return Objects.hash(commerceCode, buyOrder, amount, installmentsNumber);
    }
  }
}
