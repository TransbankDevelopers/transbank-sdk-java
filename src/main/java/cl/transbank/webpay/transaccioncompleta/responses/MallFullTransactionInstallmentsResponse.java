package cl.transbank.webpay.transaccioncompleta.responses;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class represents a response to an installments operation for a Mall Full transaction.
 */
public class MallFullTransactionInstallmentsResponse {

  private List<MallFullTransactionInstallmentResponse> responseList = new ArrayList<>();

  private MallFullTransactionInstallmentsResponse() {}

  /**
   * Builds a new MallFullTransactionInstallmentsResponse.
   */
  public static MallFullTransactionInstallmentsResponse build() {
    return new MallFullTransactionInstallmentsResponse();
  }

  /**
   * Builds a new MallFullTransactionInstallmentsResponse with the provided list of responses.
   */
  public static MallFullTransactionInstallmentsResponse build(
    MallFullTransactionInstallmentResponse response
  ) {
    return MallFullTransactionInstallmentsResponse.build().add(response);
  }

  /**
   * Adds a new item to the response list.
   */
  public MallFullTransactionInstallmentsResponse add(
    MallFullTransactionInstallmentResponse response
  ) {
    responseList.add(response);
    return this;
  }

  /**
   * Removes an item from the response list.
   */
  public boolean remove(MallFullTransactionInstallmentResponse response) {
    return getResponseList().remove(response);
  }

  /**
   * Returns the response list.
   */
  public List<MallFullTransactionInstallmentResponse> getResponseList() {
    return Collections.unmodifiableList(responseList);
  }
}
