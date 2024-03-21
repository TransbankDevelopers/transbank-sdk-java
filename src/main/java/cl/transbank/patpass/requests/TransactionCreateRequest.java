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
   * @param serviceId The service ID.
   * @param cardHolderId The card holder ID.
   * @param cardHolderName The card holder name.
   * @param cardHolderLastName1 The card holder's first last name.
   * @param cardHolderLastName2 The card holder's second last name.
   * @param cardHolderMail The card holder's mail.
   * @param cellphoneNumber The cellphone number.
   * @param expirationDate The expiration date.
   * @param commerceMail The commerce mail.
   * @param ufFlag The UF flag.
   */

  public void setDetails(Detail detail) {
    wpmDetail = detail;
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

    public String getServiceId() {
      return serviceId;
    }

    public void setServiceId(String serviceId) {
      this.serviceId = serviceId;
    }

    public String getCardHolderId() {
      return cardHolderId;
    }

    public void setCardHolderId(String cardHolderId) {
      this.cardHolderId = cardHolderId;
    }

    public String getCardHolderName() {
      return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
      this.cardHolderName = cardHolderName;
    }

    public String getCardHolderLastName1() {
      return cardHolderLastName1;
    }

    public void setCardHolderLastName1(String cardHolderLastName1) {
      this.cardHolderLastName1 = cardHolderLastName1;
    }

    public String getCardHolderLastName2() {
      return cardHolderLastName2;
    }

    public void setCardHolderLastName2(String cardHolderLastName2) {
      this.cardHolderLastName2 = cardHolderLastName2;
    }

    public String getCardHolderMail() {
      return cardHolderMail;
    }

    public void setCardHolderMail(String cardHolderMail) {
      this.cardHolderMail = cardHolderMail;
    }

    public String getCellphoneNumber() {
      return cellphoneNumber;
    }

    public void setCellphoneNumber(String cellphoneNumber) {
      this.cellphoneNumber = cellphoneNumber;
    }

    public String getExpirationDate() {
      return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
      this.expirationDate = expirationDate;
    }

    public String getCommerceMail() {
      return commerceMail;
    }

    public void setCommerceMail(String commerceMail) {
      this.commerceMail = commerceMail;
    }

    public boolean isUfFlag() {
      return ufFlag;
    }

    public void setUfFlag(boolean ufFlag) {
      this.ufFlag = ufFlag;
    }
  }
}
