package cl.transbank.patpass;

import cl.transbank.common.IntegrationType;
import cl.transbank.model.Options;
import cl.transbank.patpass.model.PatpassOptions;

/**
 * This class represents the details of a MallTransactionCommit.
 */
public class PatpassComercio {

  /**
   * This class represents the details of a MallTransactionCommit.
   */
  public static class Inscription extends PatpassComercioInscription {

    /**
     * Constructor with options. Uses provided options.
     * @param options The options to use for this transaction.
     */
    public Inscription(Options options) {
      super(options);
    }
  }

  /**
   * Creates and returns an instance of `Inscription` configured for the integration environment.
   *
   * @param commerceCode The commerce code.
   * @param apiKey The API key used for authentication.
   * @return A new instance of `Inscription` configured for the test environment (IntegrationType.TEST).
   */
  public static Inscription buildForIntegration(String commerceCode, String apiKey)
  {
    return new Inscription(new PatpassOptions(commerceCode, apiKey, IntegrationType.TEST));
  }

  /**
   * Creates and returns an instance of `Inscription` configured for the production environment.
   *
   * @param commerceCode The commerce code.
   * @param apiKey The API key used for authentication.
   * @return A new instance of `Inscription` configured for the production environment (IntegrationType.LIVE).
   */
  public static Inscription buildForProduction(String commerceCode, String apiKey)
  {
    return new Inscription(new PatpassOptions(commerceCode, apiKey, IntegrationType.LIVE));
  }

}
