package cl.transbank.webpay.webpayplus;

import cl.transbank.common.IntegrationType;
import cl.transbank.model.Options;
import cl.transbank.webpay.common.WebpayOptions;

/**
 * This class provides methods to configure and perform transactions with the WebpayPlus service.
 */
public class WebpayPlus {

  /**
   * This class provides methods to perform WebpayPlus Transactions.
   */
  public static class Transaction extends WebpayTransaction {

    /**
     * Constructor with options. Uses provided options.
     * @param options The options to use for this transaction.
     */
    public Transaction(Options options) {
      super(options);
    }

    /**
     * Creates and returns an instance of `Transaction` configured for the integration environment.
     *
     * @param commerceCode The commerce code.
     * @param apiKey The API key used for authentication.
     * @return A new instance of `Transaction` configured for the test environment (IntegrationType.TEST).
     */
    public static Transaction buildForIntegration(String commerceCode, String apiKey)
    {
      return new Transaction(new WebpayOptions(commerceCode, apiKey, IntegrationType.TEST));
    }

    /**
     * Creates and returns an instance of `Transaction` configured for the production environment.
     *
     * @param commerceCode The commerce code.
     * @param apiKey The API key used for authentication.
     * @return A new instance of `Transaction` configured for the production environment (IntegrationType.LIVE).
     */
    public static Transaction buildForProduction(String commerceCode, String apiKey)
    {
      return new Transaction(new WebpayOptions(commerceCode, apiKey, IntegrationType.LIVE));
    }
  }

  /**
   * This class provides methods to perform WebpayPlus Mall Transactions.
   */
  public static class MallTransaction extends WebpayMallTransaction {

    /**
     * Constructor with options. Uses provided options.
     * @param options The options to use for this transaction.
     */
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
      return new MallTransaction(new WebpayOptions(commerceCode, apiKey, IntegrationType.TEST));
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
      return new MallTransaction(new WebpayOptions(commerceCode, apiKey, IntegrationType.LIVE));
    }
  }

}
