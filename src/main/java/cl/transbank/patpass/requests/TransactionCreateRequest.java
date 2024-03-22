package cl.transbank.patpass.requests;

import cl.transbank.model.WebpayApiRequest;
import lombok.*;

/**
 * This class represents a request to create a transaction.
 */
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class TransactionCreateRequest extends WebpayApiRequest {

  @NonNull
  private String buyOrder;

  @NonNull
  private String sessionId;

  @NonNull
  private double amount;

  @NonNull
  private String returnUrl;

  private Detail wpmDetail;

  /**
   * Sets the details for the transaction.
   * @param detail The details for the transaction.
   */

  public void setDetails(
    String serviceId,
    String cardHolderId,
    String cardHolderName,
    String cardHolderLastName1,
    String cardHolderLastName2,
    String cardHolderMail,
    String cellphoneNumber,
    String expirationDate,
    String commerceMail,
    boolean ufFlag
  ) {
    wpmDetail =
      new Detail(
        serviceId,
        cardHolderId,
        cardHolderName,
        cardHolderLastName1,
        cardHolderLastName2,
        cardHolderMail,
        cellphoneNumber,
        expirationDate,
        commerceMail,
        ufFlag
      );
  }

  /**
   * This class represents the detail of a transaction.
   */
  @NoArgsConstructor
  @AllArgsConstructor
  @Getter
  @Setter
  @ToString
  public class Detail {

    private String serviceId;
    private String cardHolderId;
    private String cardHolderName;
    private String cardHolderLastName1;
    private String cardHolderLastName2;
    private String cardHolderMail;
    private String cellphoneNumber;
    private String expirationDate;
    private String commerceMail;
    private boolean ufFlag;
  }
}
