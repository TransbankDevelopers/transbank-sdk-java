package cl.transbank.patpass;

import cl.transbank.common.*;
import cl.transbank.model.Options;
import cl.transbank.patpass.model.PatpassOptions;
import cl.transbank.webpay.common.WebpayOptions;

/**
 * This class provides methods to configure and perform transactions with the PatpassByWebpay service.
 */
public class PatpassByWebpay {

  private static Options options;

  /**
   * This class provides methods to configure and perform transactions with the PatpassByWebpay service.
   */
  public static class Transaction extends PatpassByWebpayTransaction {

    public Transaction() {
      this.options =
        PatpassByWebpay.options != null
          ? PatpassByWebpay.options
          : new WebpayOptions(
            IntegrationCommerceCodes.PATPASS_BY_WEBPAY,
            IntegrationApiKeys.WEBPAY,
            IntegrationType.TEST
          );
    }

    public Transaction(Options options) {
      this.options = options;
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
    PatpassByWebpay.options =
      new PatpassOptions(commerceCode, apiKey, IntegrationType.TEST);
  }

  /**
   * Configures the environment for integration.
   * @param commerceCode The commerce code.
   * @param apiKey The API key.
   */
  public static void configureForProduction(
    String commerceCode,
    String apiKey
  ) {
    PatpassByWebpay.options =
      new PatpassOptions(commerceCode, apiKey, IntegrationType.LIVE);
  }

  /**
   * Configures the environment for testing.
   * @param commerceCode The commerce code.
   * @param apiKey The API key.
   */
  public static void configureForTesting() {
    configureForIntegration(
      IntegrationCommerceCodes.PATPASS_BY_WEBPAY,
      IntegrationApiKeys.WEBPAY
    );
  }

  /**
   * Configures the environment for testing deferred transactions.
   * @param commerceCode The commerce code.
   * @param apiKey The API key.
   */
  public static void configureForTestingDeferred() {
    configureForIntegration(
      IntegrationCommerceCodes.PATPASS_BY_WEBPAY,
      IntegrationApiKeys.WEBPAY
    );
  }

  /**
   * Configures the environment for mock testing.
   * @param commerceCode The commerce code.
   * @param apiKey The API key.
   */
  public static void configureForMock() {
    PatpassByWebpay.options =
      new PatpassOptions(
        IntegrationCommerceCodes.PATPASS_BY_WEBPAY,
        IntegrationApiKeys.WEBPAY,
        IntegrationType.SERVER_MOCK
      );
  }
}
