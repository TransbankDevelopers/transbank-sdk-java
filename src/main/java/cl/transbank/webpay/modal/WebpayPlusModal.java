package cl.transbank.webpay.modal;

import cl.transbank.common.IntegrationApiKeys;
import cl.transbank.common.IntegrationCommerceCodes;
import cl.transbank.common.IntegrationType;
import cl.transbank.model.Options;
import cl.transbank.webpay.common.WebpayOptions;

/**
 * This class provides methods to configure and perform transactions with the PatpassByWebpay service.
 */
public class WebpayPlusModal {

  private static Options options;

  /**
   * This class provides methods to configure and perform transactions with the WebpayPlusModal service.
   */
  public static class Transaction extends WebpayModalTransaction {

    /**
     * Default constructor. Uses default options if none are provided.
     */
    public Transaction() {
      super(
        WebpayPlusModal.options != null
          ? WebpayPlusModal.options
          : new WebpayOptions(
            IntegrationCommerceCodes.WEBPAY_PLUS_MODAL,
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

  /*
    |--------------------------------------------------------------------------
    | Environment Configuration
    |--------------------------------------------------------------------------
    */

  /**
   * Configures the environment for integration.
   * @param commerceCode The commerce code.
   * @param apiKey The API key.
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
      IntegrationCommerceCodes.WEBPAY_PLUS_MODAL,
      IntegrationApiKeys.WEBPAY
    );
  }

  public static void configureForMock() {
    options =
      new WebpayOptions(
        IntegrationCommerceCodes.WEBPAY_PLUS_MODAL,
        IntegrationApiKeys.WEBPAY,
        IntegrationType.SERVER_MOCK
      );
  }
}
