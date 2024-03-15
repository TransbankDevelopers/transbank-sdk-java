package cl.transbank.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import lombok.*;

/**
 * This class represents the details of the FullTransaction installment response.
 */
public class MallTransactionCreateDetails {

  private List<MallTransactionCreateDetails.Detail> detailList = new ArrayList<>();

  /**
   * This class represents the details of the FullTransaction installment response.
   */
  private MallTransactionCreateDetails() {}

  /**
   * Builds a new MallTransactionCreateDetails instance.
   * @return A new MallTransactionCreateDetails instance.
   */
  public static MallTransactionCreateDetails build() {
    return new MallTransactionCreateDetails();
  }

  /**
   * Builds a new MallTransactionCreateDetails instance and adds the provided detail.
   * @param amount The amount of the transaction.
   * @param commerceCode The commerce code.
   * @param buyOrder The buy order.
   * @return A new MallTransactionCreateDetails instance with the provided detail added.
   */
  public static MallTransactionCreateDetails build(
    double amount,
    String commerceCode,
    String buyOrder
  ) {
    return MallTransactionCreateDetails
      .build()
      .add(amount, commerceCode, buyOrder);
  }

  /**
   * Adds a new detail to this MallTransactionCreateDetails.
   * @param amount The amount of the transaction.
   * @param commerceCode The commerce code.
   * @param buyOrder The buy order.
   * @return This MallTransactionCreateDetails instance.
   */
  public MallTransactionCreateDetails add(
    double amount,
    String commerceCode,
    String buyOrder
  ) {
    detailList.add(
      new MallTransactionCreateDetails.Detail(amount, commerceCode, buyOrder)
    );
    return this;
  }

  /**
   * Removes a detail from this MallTransactionCreateDetails.
   * @param amount The amount of the transaction.
   * @param commerceCode The commerce code.
   * @param buyOrder The buy order.
   * @return True if the detail was removed, false otherwise.
   */
  public boolean remove(double amount, String commerceCode, String buyOrder) {
    return getDetails()
      .remove(
        new MallTransactionCreateDetails.Detail(amount, commerceCode, buyOrder)
      );
  }

  /**
   * Gets the details of this MallTransactionCreateDetails.
   * @return The details of this MallTransactionCreateDetails.
   */
  public List<MallTransactionCreateDetails.Detail> getDetails() {
    return Collections.unmodifiableList(detailList);
  }

  /**
   * Gets the details of this MallTransactionCreateDetails.
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

    /**
     * Checks if this Detail is equal to another object.
     * @param obj The object to compare with.
     * @return True if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
      if (!(obj instanceof MallTransactionCreateDetails.Detail)) return false;

      MallTransactionCreateDetails.Detail another = (MallTransactionCreateDetails.Detail) obj;
      return (
        getAmount() == another.getAmount() &&
        getCommerceCode().equals(another.getBuyOrder()) &&
        getBuyOrder().equals(another.getBuyOrder())
      );
    }

    @Override
    public int hashCode() {
      return Objects.hash(commerceCode, buyOrder, amount);
    }
  }
}
