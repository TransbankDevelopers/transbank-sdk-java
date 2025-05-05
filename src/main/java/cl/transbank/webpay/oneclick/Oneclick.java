package cl.transbank.webpay.oneclick;

import cl.transbank.common.IntegrationType;
import cl.transbank.model.Options;
import cl.transbank.webpay.common.WebpayOptions;

/**
 * This class provides methods to configure and perform transactions with the Oneclick service.
 */
public class Oneclick {

  /**
   * This class provides methods to perform Oneclick Mall Inscriptions.
   */
  public static class MallInscription extends OneclickMallInscription {

    public MallInscription(Options options) {
      super(options);
    }

    /**
     * Creates and returns an instance of `MallInscription` configured for the integration environment.
     *
     * @param commerceCode The commerce code.
     * @param apiKey The API key used for authentication.
     * @return A new instance of `MallInscription` configured for the test environment (IntegrationType.TEST).
     */
    public static MallInscription buildForIntegration(String commerceCode, String apiKey)
    {
      return new Oneclick.MallInscription(new WebpayOptions(commerceCode, apiKey, IntegrationType.TEST));
    }

    /**
     * Creates and returns an instance of `MallInscription` configured for the production environment.
     *
     * @param commerceCode The commerce code.
     * @param apiKey The API key used for authentication.
     * @return A new instance of `MallInscription` configured for the production environment (IntegrationType.LIVE).
     */
    public static MallInscription buildForProduction(String commerceCode, String apiKey)
    {
      return new Oneclick.MallInscription(new WebpayOptions(commerceCode, apiKey, IntegrationType.LIVE));
    }
  }

  /**
   * Default constructor. Uses default options if none are provided.
   */
  public static class MallTransaction extends OneclickMallTransaction {

    public MallTransaction(Options options) {
      super(options);
    }

    /**
     * Creates and returns an instance of `MallTransaction` configured for the integration environment.
     *
     * @param commerceCode The commerce code.
     * @param apiKey The API key used for authentication.
     * @return A new instance of `MallTransaction` configured for the test environment (IntegrationType.TEST).
     */
    public static MallTransaction buildForIntegration(String commerceCode, String apiKey)
    {
      return new Oneclick.MallTransaction(new WebpayOptions(commerceCode, apiKey, IntegrationType.TEST));
    }

    /**
     * Creates and returns an instance of `MallTransaction` configured for the production environment.
     *
     * @param commerceCode The commerce code.
     * @param apiKey The API key used for authentication.
     * @return A new instance of `MallTransaction` configured for the production environment (IntegrationType.LIVE).
     */
    public static MallTransaction buildForProduction(String commerceCode, String apiKey)
    {
      return new Oneclick.MallTransaction(new WebpayOptions(commerceCode, apiKey, IntegrationType.LIVE));
    }
  }

}
