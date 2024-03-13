package cl.transbank.patpass;

import cl.transbank.common.*;
import cl.transbank.model.Options;
import cl.transbank.patpass.model.PatpassOptions;

/**
 * This class represents the details of a MallTransactionCommit.
 */
public class PatpassComercio {

  private static Options options;

  /**
   * This class represents the details of a MallTransactionCommit.
   */
  public static class Inscription extends PatpassComercioInscription {

    /**
     * Default constructor. Uses default options if none are provided.
     */
    public Inscription() {
      this.options =
        PatpassComercio.options != null
          ? PatpassComercio.options
          : new PatpassOptions(
            IntegrationCommerceCodes.PATPASS_COMERCIO,
            IntegrationApiKeys.PATPASS_COMERCIO,
            IntegrationType.TEST
          );
    }

    /**
     * Constructor with options. Uses provided options.
     * @param options The options to use for this transaction.
     */
    public Inscription(Options options) {
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
    PatpassComercio.options =
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
    PatpassComercio.options =
      new PatpassOptions(commerceCode, apiKey, IntegrationType.LIVE);
  }

  /**
   * Configures the environment for testing.
   * @param commerceCode The commerce code.
   * @param apiKey The API key.
   */
  public static void configureForTesting() {
    configureForIntegration(
      IntegrationCommerceCodes.PATPASS_COMERCIO,
      IntegrationApiKeys.PATPASS_COMERCIO
    );
  }

  /**
   * Configures the environment for testing deferred transactions.
   * @param commerceCode The commerce code.
   * @param apiKey The API key.
   */
  public static void configureForTestingDeferred() {
    configureForIntegration(
      IntegrationCommerceCodes.PATPASS_COMERCIO,
      IntegrationApiKeys.PATPASS_COMERCIO
    );
  }

  /**
   * Configures the environment for mock testing.
   * @param commerceCode The commerce code.
   * @param apiKey The API key.
   */
  public static void configureForMock() {
    PatpassComercio.options =
      new PatpassOptions(
        IntegrationCommerceCodes.PATPASS_COMERCIO,
        IntegrationApiKeys.PATPASS_COMERCIO,
        IntegrationType.SERVER_MOCK
      );
  }
}
