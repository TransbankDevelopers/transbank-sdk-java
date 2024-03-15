package cl.transbank.patpass.responses;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.*;

/**
 * Configures the environment for mock testing.
 */
public class PatpassTransactionCreateDetails {

  private List<Detail> detailList = new ArrayList<>();

  /**
   * Configures the environment for mock testing.
   */
  private PatpassTransactionCreateDetails() {}

  /**
   * Configures the environment for mock testing.
   */
  public static PatpassTransactionCreateDetails build() {
    return new PatpassTransactionCreateDetails();
  }

  /**
   * Builds a new PatpassTransactionCreateDetails with the provided parameters.
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
   * @return A new PatpassTransactionCreateDetails.
   */
  public static PatpassTransactionCreateDetails build(
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
    return PatpassTransactionCreateDetails
      .build()
      .add(
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
   * Adds a new detail to the PatpassTransactionCreateDetails.
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
   * @return The PatpassTransactionCreateDetails with the new detail added.
   */
  public PatpassTransactionCreateDetails add(
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
    detailList.add(
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
      )
    );
    return this;
  }

  public List<Detail> getDetails() {
    return Collections.unmodifiableList(detailList);
  }

  /**
   * This class represents the details of a PatpassTransactionCreate.
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
