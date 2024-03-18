package cl.transbank.common;

/**
 * This class contains the constants used in the API.
 */
public class ApiConstants {

  public static final String WEBPAY_ENDPOINT =
    "rswebpaytransaction/api/webpay/v1.2";
  public static final String ONECLICK_ENDPOINT =
    "rswebpaytransaction/api/oneclick/v1.2";
  public static final String PATPASS_COMERCIO_ENDPOINT =
    "restpatpass/v1/services";
  public static final String HEADER_COMMERCE_CODE_NAME = "Tbk-Api-Key-Id";
  public static final String HEADER_API_KEY_NAME = "Tbk-Api-Key-Secret";
  public static final int BUY_ORDER_LENGTH = 26;
  public static final int SESSION_ID_LENGTH = 61;
  public static final int RETURN_URL_LENGTH = 255;
  public static final int AUTHORIZATION_CODE_LENGTH = 6;
  public static final int CARD_EXPIRATION_DATE_LENGTH = 5;
  public static final int CARD_NUMBER_LENGTH = 19;
  public static final int TBK_USER_LENGTH = 40;
  public static final int USER_NAME_LENGTH = 40;
  public static final int COMMERCE_CODE_LENGTH = 12;
  public static final int TOKEN_LENGTH = 64;
  public static final int EMAIL_LENGTH = 100;

  /**
   * Private constructor to prevent instantiation.
   */
  private ApiConstants() {
    // This constructor is intentionally empty. Nothing to see here.
  }
}
