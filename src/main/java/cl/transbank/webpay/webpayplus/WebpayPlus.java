package cl.transbank.webpay.webpayplus;

import cl.transbank.common.IntegrationApiKeys;
import cl.transbank.common.IntegrationCommerceCodes;
import cl.transbank.common.IntegrationType;
import cl.transbank.model.Options;
import cl.transbank.webpay.common.WebpayOptions;

/**
 * This class provides methods to configure and perform transactions with the WebpayPlus service.
 */
public class WebpayPlus {

  private static Options options;

  /**
   * This class provides methods to perform WebpayPlus Transactions.
   */
  public static class Transaction extends WebpayTransaction {

    /**
     * Default constructor. Uses default options if none are provided.
     */
    public Transaction() {
      super(
        WebpayPlus.options != null
          ? WebpayPlus.options
          : new WebpayOptions(
            IntegrationCommerceCodes.WEBPAY_PLUS,
            IntegrationApiKeys.WEBPAY,
            IntegrationType.TEST
          )
      );
    }

    /**
     * Constructor with options. Uses provided options.
     * @param options The options to use for this transaction.
     */
    public Transaction(Options options) {
      super(options);
    }
  }

  /**
   * This class provides methods to perform WebpayPlus Mall Transactions.
   */
  public static class MallTransaction extends WebpayMallTransaction {

    /**
     * Default constructor. Uses default options if none are provided.
     */
    public MallTransaction() {
      super(
        WebpayPlus.options != null
          ? WebpayPlus.options
          : new WebpayOptions(
            IntegrationCommerceCodes.WEBPAY_PLUS_MALL,
            IntegrationApiKeys.WEBPAY,
            IntegrationType.TEST
          )
      );
    }

    /**
     * Constructor with options. Uses provided options.
     * @param options The options to use for this transaction.
     */
    public MallTransaction(Options options) {
      super(options);
    }
  }

  /*



    |--------------------------------------------------------------------------
    | Environment Configuration
    |--------------------------------------------------------------------------
    */

  public static void configureForIntegration(
    String commerceCode,
    String apiKey
  ) {
    options = new WebpayOptions(commerceCode, apiKey, IntegrationType.TEST);
  }

  public static void configureForProduction(
    String commerceCode,
    String apiKey
  ) {
    options = new WebpayOptions(commerceCode, apiKey, IntegrationType.LIVE);
  }

  public static void configureForTesting() {
    configureForIntegration(
      IntegrationCommerceCodes.WEBPAY_PLUS,
      IntegrationApiKeys.WEBPAY
    );
  }

  public static void configureForTestingDeferred() {
    configureForIntegration(
      IntegrationCommerceCodes.WEBPAY_PLUS_DEFERRED,
      IntegrationApiKeys.WEBPAY
    );
  }

  public static void configureForTestingMall() {
    configureForIntegration(
      IntegrationCommerceCodes.WEBPAY_PLUS_MALL,
      IntegrationApiKeys.WEBPAY
    );
  }

  public static void configureForTestingMallDeferred() {
    configureForIntegration(
      IntegrationCommerceCodes.WEBPAY_PLUS_MALL_DEFERRED,
      IntegrationApiKeys.WEBPAY
    );
  }

  public static void configureForMock() {
    options =
      new WebpayOptions(
        IntegrationCommerceCodes.WEBPAY_PLUS,
        IntegrationApiKeys.WEBPAY,
        IntegrationType.SERVER_MOCK
      );
  }
}
